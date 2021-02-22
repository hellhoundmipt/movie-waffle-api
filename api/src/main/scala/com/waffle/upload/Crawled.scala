package com.waffle.upload

final case class Crawled(
    url: String, 
    title: String, 
    year: String, 
    starring: Seq[String], 
    summary: Seq[String], 
    posterUrl: String, 
    genres: Seq[String], 
    directors: Seq[String],
    rating: String,
    runtime: String,
    mymlUrls: Seq[String],
    metascore: String,
    userScores: String
)

object CrawledJsonProtocol
    extends akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
    with spray.json.DefaultJsonProtocol {

    implicit val crawledFormat = jsonFormat13(Crawled.apply)
}