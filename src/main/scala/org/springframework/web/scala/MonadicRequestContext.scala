package org.springframework.web.scala

import javax.servlet.http.HttpServletRequest

trait MonadicRequestContext[T] {

  def getOriginalRequest():HttpServletRequest

  def read():T

  def write(fn : T => Unit):Unit

}

class WrappedRequestContext[T,O](val newValue:T,val origin:MonadicRequestContext[O]) extends MonadicRequestContext[T]{

  override def read(): T = newValue

  override def write(fn: (T) => Unit): Unit = fn(newValue)

  override def getOriginalRequest(): HttpServletRequest = origin.getOriginalRequest()
}

class WrappedServletRequestContext[T](val newValue:T,val origin:HttpServletRequest) extends MonadicRequestContext[T]{

  override def read(): T = newValue

  override def write(fn: (T) => Unit): Unit = fn(newValue)

  override def getOriginalRequest(): HttpServletRequest = origin
}
