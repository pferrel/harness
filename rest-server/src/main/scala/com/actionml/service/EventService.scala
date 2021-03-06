package com.actionml.service

import com.actionml.ActorInjectable
import com.actionml.entity.Event
import scaldi.Injector

/**
  *
  *
  * @author The ActionML Team (<a href="http://actionml.com">http://actionml.com</a>)
  * 28.01.17 14:49
  */

case class EventId(eventId: String)

trait EventService extends ActorInjectable

class EmptyEventService(implicit inj: Injector) extends EventService{
  override def receive: Receive = {
    case GetEvent(datasetId, eventId) ⇒
      log.info("Get entity, {}, {}", datasetId, eventId)
      sender() ! None

    case CreateEvent(datasetId, event) ⇒
      log.info("Create new entity, {}, {}", datasetId, event)
      sender() ! None
  }
}

sealed trait EventAction
case class GetEvent(datasetId: String, eventId: String) extends EventAction
case class CreateEvent(datasetId: String, event: Event) extends EventAction
