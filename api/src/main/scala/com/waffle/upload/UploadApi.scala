package com.waffle.upload

import akka.actor.typed.ActorSystem
import scala.concurrent.ExecutionContextExecutor
import akka.http.scaladsl.common.EntityStreamingSupport
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.waffle.upload.CrawledJsonProtocol._
import com.waffle.upload.Crawled
import scala.concurrent.ExecutionContext

object UploadApi {

    implicit val jsonStreamingSupport = EntityStreamingSupport.json()
    
    def route()(implicit system: ActorSystem[_], ec: ExecutionContext) = {
        (pathPrefix("upload") & post & extractLog & entity(asSourceOf[Crawled])) { (log, items) =>
            val itemsSubmitted = items.runFold(0){ (cnt, _) => cnt + 1 }
            complete(
                itemsSubmitted.map(n => s"Total tings: $n")
            )
        }   
    }
}
