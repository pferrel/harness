package com.actionml.http.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCode
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Directive, Route}
import akka.util.Timeout
import de.heikoseeberger.akkahttpcirce.CirceSupport
import io.circe.Json
import scaldi.Injector
import scaldi.akka.AkkaInjectable

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

/**
  *
  *
  * @author The ActionML Team (<a href="http://actionml.com">http://actionml.com</a>)
  * 29.01.17 16:15
  */
abstract class BaseRouter(implicit inj: Injector) extends AkkaInjectable with CirceSupport {

  implicit protected val actorSystem: ActorSystem = inject[ActorSystem]
  implicit protected val executor: ExecutionContext = actorSystem.dispatcher
  implicit val timeout = Timeout(5 seconds)

  protected val putOrPost: Directive[Unit] = post | put

  def completeByCond(
    ifDefinedStatus: StatusCode,
    ifEmptyStatus: StatusCode
  )(resourceId: Future[Option[Json]]): Route =
    onSuccess(resourceId) {
      case Some(json) => complete(ifDefinedStatus, json)
      case None => complete(ifEmptyStatus, None)
    }

  val route: Route

}
