package com.waffle


import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.waffle.upload.UploadApi
import akka.actor.typed.DispatcherSelector
import akka.stream.alpakka.slick.scaladsl.SlickSession
import scala.concurrent.Await
import scala.concurrent.duration._

object ApiMain extends App {
    val address = "localhost"
    val port = 8000
    implicit val system = ActorSystem(Behaviors.empty, "api-system")
    implicit val executionContext = system.executionContext
    implicit val db = slick.jdbc.JdbcBackend.Database.forConfig("mydb")

    val baseRoute = (pathEndOrSingleSlash & get) {
        complete(StatusCodes.OK)
    }
    
    val uploadRoute = UploadApi.route()(system, system.dispatchers.lookup(DispatcherSelector.fromConfig("stream-dispatcher")))

    val bindingFuture = Http().newServerAt(address, port).bind(baseRoute ~ uploadRoute)

    Thread.sleep(20000)
    println("Gracefully shutting down")
    Await.result(bindingFuture, 10.seconds).terminate(hardDeadline = 3.seconds).foreach{ _ =>
        db.close()
        system.terminate()
    }
    
}
