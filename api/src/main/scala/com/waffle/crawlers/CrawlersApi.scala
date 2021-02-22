package com.waffle.crawlers

import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.actor.typed.ActorSystem
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.client.RequestBuilding.{Post, Get}

object CrawlersApi extends CrawlerJsonProtocol with SprayJsonSupport {
    private val scrapydAddress = "crawler"
    private val scrapydPort = 6800
    private val scrapydUrl = s"http://$scrapydAddress:$scrapydPort"
    private val scrapyProject = "default"
    private val spiderName = "metacritic_movies"
    private lazy val statusUrl = s"$scrapydUrl/listspiders.json?project=$scrapyProject"
    private lazy val scheduleUrl = s"$scrapydUrl/schedule.json"
    private lazy val cancelUrl = s"$scrapydUrl/cancel.json"

    def status()(implicit system: ActorSystem[_], ex: ExecutionContext) = {
        val request = Get(statusUrl)
        Http().singleRequest(request).flatMap{
            case response@HttpResponse(StatusCodes.OK, _, entity, _) => Unmarshal(entity).to[ListJobsRequest]
            case _ => Future.failed(new RuntimeException("Could not fetch status"))
        }
    }

    def start()(implicit system: ActorSystem[_], ex: ExecutionContext) = {
        lazy val scheduleRequest = Post(scheduleUrl, ScheduleRequest(scrapyProject, spiderName))
        for {
            status <- status()
            scheduleResponse: ScheduleResult <- if (status.spiderScheduled(spiderName)) Future(ScheduleFailure("Spider is already running")) else Http().singleRequest(scheduleRequest).flatMap{
                case response@HttpResponse(StatusCodes.OK, _, _, _) => Future(ScheduleSuccess)
                case _ => Future.failed(new RuntimeException("Could not schedule spider"))
            }
        } yield scheduleResponse
    }
}
