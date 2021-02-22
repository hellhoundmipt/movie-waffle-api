package com.waffle.crawlers

sealed trait CrawlerApiComm 

sealed trait ScheduleResult extends CrawlerApiComm
case object ScheduleSuccess extends ScheduleResult
case class ScheduleFailure(reason: String) extends ScheduleResult
