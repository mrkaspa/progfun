package week8

import java.util.concurrent.Executor

import akka.actor._
import akka.actor.Actor.Receive
import akka.actor.Status.{Failure, Success}
import com.ning.http.client.AsyncHttpClient
import akka.pattern.pipe

import scala.concurrent.{ExecutionContext, Promise, Future}
import scala.concurrent.duration._
import scala.util.matching.Regex

/**
 * Created by michelperez on 3/7/15.
 */
object HttpLinks extends App {

    val system = ActorSystem("system")

    val mainActor = system.actorOf(Props[MainActor])

}

object WebClient {
  val client = new AsyncHttpClient

  def get(url: String)(implicit exec: Executor): Future[String] = {
    val f = client.prepareGet(url).execute()
    val p = Promise[String]()
    f.addListener(new Runnable {
      override def run() = {
        val response = f.get
        if (response.getStatusCode < 400)
          p.success(response.getResponseBodyExcerpt(131072))
        else
          p.failure(new Exception)
      }
    }, exec)
    p.future
  }

  def shutdown = {
    client.close()
  }

}

object Getter {

  object Done

  object Abort

}

class Getter(url: String, depth: Int) extends Actor {

  import Getter._

  val A_TAG = "(?i)<a ([^>]+)>.+?</a>".r
  val HREF_ATTR = """\s*(?i)href\s*=\s*(?:"([^"]*)"|'([^']*)'|([^'">\s]+))""".r
  implicit val exec = context.dispatcher.asInstanceOf[Executor with ExecutionContext]
  WebClient get url pipeTo self

  override def receive: Receive = {
    case body: String =>
      for (link <- findLinks(body))
        context.parent ! Controller.Check(link, depth)
    case Abort => stop()
    case _: Status.Failure => stop()
  }

  def stop() = {
    context.parent ! Done
    context.stop(self)
  }

  def findLinks(body: String): Iterator[String] = {
    for {
      anchor <- A_TAG.findAllMatchIn(body)
      HREF_ATTR(dquot, quot, bare) <- anchor.subgroups
    } yield
      if (dquot != null) dquot
      else if (quot != null) quot
      else bare
  }

}

object Controller {

  case class Check(url: String, depth: Int)

  case class Result(cache: Set[String])

}

class Controller extends Actor with ActorLogging {

  import Controller._

  context.setReceiveTimeout(10 seconds)
  var cache = Set.empty[String]
  var children = Set.empty[ActorRef]

  override def receive: Actor.Receive = {
    case Check(url, depth) =>
      log.debug("{} checking {}", depth, url)
      if (!cache(url) && depth > 0)
        children += context.actorOf(Props(new Getter(url, depth - 1)))
      cache += url
    case Getter.Done =>
      children -= sender
      if (children.isEmpty) context.parent ! Result(cache)
    case ReceiveTimeout => children foreach (_ ! Getter.Abort)
  }

}

case class Job(client: ActorRef, url: String)

object Recepcionist {

  case class Get(url: String)

  case class Failed(url: String)

  case class Result(url: String, links: Set[String])

}

class Recepcionist extends Actor {

  import Recepcionist._

  var reqNo = 0

  def receive = waiting

  def waiting: Receive = {
    case Get(url) => context.become(runNext(Vector(Job(sender, url))))
  }

  def running(queue: Vector[Job]): Receive = {
    case Controller.Result(links) =>
      val job = queue.head
      job.client ! Result(job.url, links)
      context.stop(sender)
      context.become(runNext(queue.tail))
    case Get(url) =>
      context.become(enqueueJob(queue, Job(sender, url)))
  }

  def runNext(queue: Vector[Job]): Receive = {
    reqNo += 1
    if (queue.isEmpty) waiting
    else {
      val controller = context.actorOf(Props[Controller], s"c$reqNo")
      controller ! Controller.Check(queue.head.url, 2)
      running(queue)
    }
  }

  def enqueueJob(queue: Vector[Job], job: Job): Receive = {
    if (queue.size > 3) {
      sender ! Failed(job.url)
      running(queue)
    } else
      running(queue :+ job)
  }

}

class MainActor extends Actor {

  import Recepcionist._

  val recepcionist = context.actorOf(Props[Recepcionist], "recepcionist")
  recepcionist ! Get("http://land-book.com/")
  context.setReceiveTimeout(10 seconds)

  override def receive: Receive = {
    case Result(url, links) =>
      println(links.toVector.sorted.mkString(s"Results for $url : \n", "\n", "\n"))
    case Failed(url) =>
      print(s"Failed to fetch $url")
    case ReceiveTimeout =>
      println("Shut Down")
      context.stop(self)
  }

  override def postStop = {
    WebClient.shutdown
  }

}