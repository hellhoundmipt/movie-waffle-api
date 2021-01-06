# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

from datetime import datetime
import pytz
import logging
import requests
import os

class ApiPipeline(object):
    def process_item(self, item, spider):
        #doc = {
        #    'album': item['album'],
        #    'artist': item['artist'],
        #    'text': item['text'],
        #    'rating': item['rating'],
        #    'album_year': item['album_year'],
        #    'genre': item['genre'],
        #    'source': item['source']
        #}
        #requests.post(os.environ.get("API_ADDRESS"), json=doc)
        return item