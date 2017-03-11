package org.springframework.web

import java.io.File

import org.apache.catalina.Context
import org.apache.catalina.startup.Tomcat
import org.junit.runner.RunWith

import collection.mutable.Stack
import org.scalatest._
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.web.scala._
import org.springframework.web.servlet.DispatcherServlet

/**
  * Created by sam on 10/03/17.
  */

class HandlerTest extends FlatSpec with Matchers  with BeforeAndAfterAll{

  val requestToString: MvcRequest => String = r => r.params.getOrElse("name","world")
  val uri: String = "/hello"
  val action =  Action(Get( uri) ){
      requestToString
  }

  val mapping:ActionHandlerMapping = new ActionHandlerMapping(Map(uri -> action))


  "A ActionHandlerMapping" should "match correct handlers" in {

    val mockRequest = new MockHttpServletRequest("GET",uri)
    val handrer = mapping.getHandlerInternal(mockRequest)

    val stack = new Stack[Int]
    stack.push(1)
    stack.push(2)
    stack.pop() should be (2)
    stack.pop() should be (1)
  }

  it should "throw RuntimeException if no mapping is found" in {
    val mockRequest = new MockHttpServletRequest("GET","emptyRoute")

    a [RuntimeException] should be thrownBy {
      val handrer = mapping.getHandlerInternal(mockRequest)
    }
  }


}
