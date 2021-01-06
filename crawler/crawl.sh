#!/bin/bash

echo Crawling Metacritic
scrapy crawl metacritic_movies --loglevel INFO
echo Finished crawling