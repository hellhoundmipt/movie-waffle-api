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

    def to_dict(self):
        return {
            "url": self['url'],
            "title": self['title'],
            "year": self['year'],
            "starring": self['starring'],
            "summary": self['summary'],
            "posterUrl": self['poster_url'],
            "genres": self['genres'],
            "directors": self['directors'],
            "rating": self['rating'],
            "runtime": self['runtime'],
            "mymlUrls": self['myml_urls'],
            "metascore": self['metascore'],
            "userScore": self['user_score']
        }

