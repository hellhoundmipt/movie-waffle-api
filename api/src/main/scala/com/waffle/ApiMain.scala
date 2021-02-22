package com.waffle


import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.waffle.crawlers.CrawlersApi
import com.waffle.crawlers.CrawlerJsonProtocol
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

object ApiMain extends App with CrawlerJsonProtocol with SprayJsonSupport {
    val address = "localhost"
    val port = 8000
    implicit val system = ActorSystem(Behaviors.empty, "api-system")
    implicit val executionContext = system.executionContext

    val route = (pathEndOrSingleSlash & get) {
        complete(StatusCodes.OK)
    }
    val crawlersRoute = 
        (pathPrefix("api" / "crawlers") & extractLog) { log => 
            get {
                path("status") {
                    complete(CrawlersApi.status())
                }
            } ~
            post {
                path("start") {
                    log.info("Starting crawling...")
                    complete(StatusCodes.OK)
                } ~
                path("stop") {
                    log.info("Stopping crawling...")
                    complete(StatusCodes.OK)
                }
            }
        }
    val upload = 
        (pathPrefix("upload") & post & extractLog) { log =>
            complete(StatusCodes.OK)
        }

    val bindingFuture = Http().newServerAt(address, port).bind(route ~ crawlersRoute ~ upload)
}
