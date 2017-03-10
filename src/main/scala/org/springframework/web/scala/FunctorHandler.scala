package org.springframework.web.scala

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

/**
  * Created by sam on 09/03/17.
  *
  * class FunctorHandler[TRequest[_]] {

  def map[A, B](a: TRequest[A])(fn: A => B): TRequest[B] = {




  }
}
*/

class FunctorHandler {

  def map[A,B](input:MonadicRequestContext[A], fn: A =>B ): MonadicRequestContext[B] = {

       val aValue:A = input.read()
       new WrappedRequestContext[B,A](fn(aValue),input)


  }


}







