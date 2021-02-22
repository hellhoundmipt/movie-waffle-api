package com.waffle


import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.waffle.upload.UploadApi

object ApiMain extends App {
    val address = "localhost"
    val port = 8000
    implicit val system = ActorSystem(Behaviors.empty, "api-system")
    implicit val executionContext = system.executionContext

    val route = (pathEndOrSingleSlash & get) {
        complete(StatusCodes.OK)
    }
    
    val upload = UploadApi.route()

    val bindingFuture = Http().newServerAt(address, port).bind(route ~ upload)
}
