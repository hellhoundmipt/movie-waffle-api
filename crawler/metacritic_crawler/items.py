# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class MovieItem(scrapy.Item):
    url = scrapy.Field()
    title = scrapy.Field()
    year = scrapy.Field()
    starring = scrapy.Field()
    summary = scrapy.Field()
    poster_url = scrapy.Field()
    genres = scrapy.Field()
    directors = scrapy.Field()
    rating = scrapy.Field()
    runtime = scrapy.Field()
    myml_urls = scrapy.Field()
    metascore = scrapy.Field()
    user_score = scrapy.Field()

