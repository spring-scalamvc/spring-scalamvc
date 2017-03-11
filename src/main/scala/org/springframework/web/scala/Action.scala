package org.springframework.web.scala

import javax.servlet.http.HttpServletRequest

import scala.collection.JavaConverters._
import scala.collection.JavaConversions._


/**
  * Created by sam on 09/03/17.
  */



trait RequestMapping[A] {

  def path:String

  def method:String

  def defaultConverter() : HttpServletRequest => MonadicRequestContext[A]

}


class Get[A](queryPath:String,fn: HttpServletRequest => A) extends RequestMapping[A] {


  override def method: String = "GET"


  override def path: String = queryPath


  override def defaultConverter(): (HttpServletRequest) => MonadicRequestContext[A] =
    r =>
    new WrappedServletRequestContext[A](fn(r),r)


}

object Get{

  def apply(path: String): Get[MvcRequest] = new Get(path,r => new MvcRequest(r))

  def apply[A](queryPath: String, fn: HttpServletRequest => A): Get[A] = new Get(queryPath, fn)
}

case class Action[A,B](val req:RequestMapping[A])( val fn:A => B) //TODO  swagger



object Action {

  /*def apply[A,B](req:RequestMapping[A])(fn:A => B): Action[A,B] = {
     new Action(req,fn)
  }*/


}

