package com.waffle.crawlers

sealed trait CrawlerComm 

sealed trait CrawlerRequest extends CrawlerComm
case class ScheduleRequest(project: String, spider: String) extends CrawlerRequest
case class CancelRequest(project: String, job: String) extends CrawlerRequest

sealed trait CrawlerResponse extends CrawlerComm
case class ListJobsRequest(status: String, pending: List[SpiderStatus], running: List[SpiderStatus], finished: List[SpiderStatus]) extends CrawlerResponse {
    def spiderScheduled(spider: String) = {
        running.exists(_.spider == spider) || pending.exists(_.spider == spider)
    }
}
case class SpiderStatus(id: String, spider: String, start_time: Option[String], end_time: Option[String])