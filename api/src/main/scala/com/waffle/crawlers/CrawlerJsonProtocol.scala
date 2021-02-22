package com.waffle.crawlers

import spray.json._

trait CrawlerJsonProtocol extends DefaultJsonProtocol {
    implicit val scheduleRequestFormat = jsonFormat2(ScheduleRequest)
    implicit val cancelRequestFormat = jsonFormat2(CancelRequest)

    implicit val spiderStatusFormat = jsonFormat4(SpiderStatus)
    implicit val listJobsRequestFormat = jsonFormat4(ListJobsRequest)

    implicit val scheduleSuccessFormat = jsonFormat1(ScheduleFailure)
}
