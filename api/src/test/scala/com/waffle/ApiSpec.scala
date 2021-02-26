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
import scala.io.Source
import org.scalatest.BeforeAndAfterEach

class ApiSpec extends AnyWordSpec with Matchers with ScalatestRouteTest with BeforeAndAfterEach {

    val testKit = ActorTestKit()
    implicit val ac = testKit.internalSystem

    override def afterAll(): Unit = testKit.shutdownTestKit()

    override protected def beforeEach(): Unit = {
        println("Gonna clear db")
    }

    private def readFile(path: String): String = Source.fromURL(getClass().getResource(path)).getLines().mkString

    "Data from crawlers" should {
        "be uploaded successfully for a single input" in {
            val input = readFile("/taxi_driver.json")
            Post("/upload").withEntity(HttpEntity(ContentTypes.`application/json`, input)) ~> UploadApi.route ~> check {
                responseAs[String] shouldEqual "Total tings: 1"
            }
        }

        "be uploaded successfully for an array input" in {
            val input = readFile("/taxi_driver_and_solaris.json")
            Post("/upload").withEntity(HttpEntity(ContentTypes.`application/json`, input)) ~> UploadApi.route ~> check {
                responseAs[String] shouldEqual "Total tings: 2"
            }
        }
    }
}
