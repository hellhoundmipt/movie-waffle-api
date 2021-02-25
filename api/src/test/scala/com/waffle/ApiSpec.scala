package com.waffle

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import com.waffle.upload.UploadApi
import akka.testkit.TestKit
import akka.actor.typed.ActorSystem
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import com.waffle.upload.Crawled
import com.waffle.upload.CrawledJsonProtocol._
import spray.json._
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.ContentTypes

class ApiSpec extends AnyWordSpec with Matchers with ScalatestRouteTest {

    val testKit = ActorTestKit()
    implicit val ac = testKit.internalSystem

    override def afterAll(): Unit = testKit.shutdownTestKit()

    "Data from crawlers" should {
        "be uploaded successfully" in {
            val input = Crawled(
                url = "http://huhu.com",
                title = "Taxi Driver",
                year = "1993",
                starring = Seq("Robert De Niro"),
                summary = Seq("On every street", "there's a nobody", "who dreams of being somebody"),
                posterUrl = "Lel",
                genres = Seq("drama"),
                directors = Seq("Martin Scorcese"),
                rating = "R",
                runtime = "90:30",
                mymlUrls = Seq(),
                metascore = "10",
                userScore = "10"
            )
            println(input.toJson)
            Post("/upload").withEntity(HttpEntity(ContentTypes.`application/json`, input.toJson.compactPrint)) ~> UploadApi.route ~> check {
                responseAs[String] shouldEqual "Total tings: 1"
            }
        }
    }
}
