# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

from crawler.metacritic_crawler.items import MovieItem
from datetime import datetime
import pytz
import logging
import requests
import os

class ApiPipeline(object):
    def process_item(self, item: MovieItem, spider):
        requests.post(os.environ.get("API_ADDRESS"), json=item.to_dict())
        return item