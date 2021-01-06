import scrapy
import logging
from metacritic_crawler.items import MovieItem
import os

class MetacriticMoviesCrawler(scrapy.Spider):
    name = 'metacritic_movies'
    start_urls = ['https://www.metacritic.com/browse/movies/score/metascore/all/filtered?sort=desc&page=']
    allowed_domains = ['metacritic.com']
    max_pages = os.environ.get('MAX_PAGES', 130)
    max_per_page = os.environ.get('MAX_PER_PAGE', -1)

    def ParsePage(self, response):
        pages = response.xpath('//a[contains(@class, "title")]//@href').extract()
        if self.max_per_page > 0:
            pages = pages[:self.max_per_page]
        for page in pages:
            yield scrapy.Request("https://www.metacritic.com" + page, callback=self.ParseMoviePage)

    def ParseMoviePage(self, response):
        url = response.url
        title = response.xpath('//div[contains(@class, "product_page_title")]//h1//text()').extract_first()
        release_year = response.xpath('//span[contains(@class, "release_year")]//text()').extract_first()
        metascore = response.xpath('//div[contains(@class, "ms_wrapper")]//span[contains(@class, "metascore_w")]//text()').extract_first()
        user_score = response.xpath('//div[contains(@class, "us_wrapper")]//span[contains(@class, "metascore_w")]//text()').extract_first()
        summary_img = response.xpath('//img[@class="summary_img"]/@src').extract_first()
        starring = response.xpath('//div[contains(@class, "summary_cast")]//a//text()').extract()[1:]
        summary = response.xpath('//div[contains(@class, "summary_deck")]//span[not(*)]//text()').extract()[1:]
        _details_section = response.xpath('//div[contains(@class, "details_section")]')
        directors = _details_section.xpath('.//div[@class="director"]//span//text()').extract()[1:]
        genres = _details_section.xpath('.//div[@class="genres"]//span[not(*)]//text()').extract()[1:]
        rating = _details_section.xpath('.//div[@class="rating"]//span[not(*)]//text()').extract()[1].strip()
        runtime = _details_section.xpath('.//div[@class="runtime"]//span[not(*)]//text()').extract()[1].strip()
        myml = response.xpath('//div[contains(@class, "battleship")]/div/a/@href').extract()
        movie_item = MovieItem(
            url=url,
            title= title,
            year=release_year,
            starring=starring,
            summary=summary,
            poster_url=summary_img,
            genres=genres,
            directors=directors,
            rating=rating,
            runtime=runtime,
            myml_urls=myml,
            metascore=metascore,
            user_score=user_score
        )
        yield movie_item

    # Movie Releases by Critic Score
    def parse(self, response):
        for page in range(0, self.max_pages):      #there are 138 pages for now
            new_page = response.url[:-1] + str(page)
            yield scrapy.Request(new_page, callback=self.ParsePage)