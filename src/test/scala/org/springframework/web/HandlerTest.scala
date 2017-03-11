package org.springframework.web

import java.io.File

import org.apache.catalina.Context
import org.apache.catalina.startup.Tomcat
import org.junit.runner.RunWith

import collection.mutable.Stack
import org.scalatest._
import org.springframework.mock.web.{MockHttpServletRequest, MockHttpServletResponse}
import org.springframework.web.scala._
import org.springframework.web.servlet.{DispatcherServlet, ModelAndView}



class HandlerTest extends FlatSpec with Matchers  with BeforeAndAfterAll{

  val requestToString: MvcRequest => String = r => r.params.getOrElse("name","world")
  val requestToPerson: MvcRequest => String = r => r.attributes.getOrElse("company","ACME")



  val helloUri: String = "/hello"
  val personUri: String = "/person/{company}/{id}"
  val helloAction =  Action(Get( helloUri) ){
      requestToString
  }

  val personAction =  Action(Get( helloUri) ){
    requestToPerson
  }


  val mapping:ActionHandlerMapping = new ActionHandlerMapping(Map(helloUri -> helloAction , personUri -> helloAction))

  val adapter = new FunctorHandlerActionAdapter


  "A ActionHandlerMapping" should "match correct handlers" in {

    val mockRequest = new MockHttpServletRequest("GET",helloUri)
    val handrer = mapping.getHandlerInternal(mockRequest)

  }

  it should "throw RuntimeException if no mapping is found" in {
    val mockRequest = new MockHttpServletRequest("GET","emptyRoute")

    a [RuntimeException] should be thrownBy {
      val handrer = mapping.getHandlerInternal(mockRequest)
    }
  }

  it should "match  handlers mapped on path parameters" in {
    val mockRequest = new MockHttpServletRequest("GET","/person/twitter/1")

    val handrer = mapping.getHandlerInternal(mockRequest)

    mockRequest.getAttribute("company") should be ("twitter")

  }

  "A FunctorHandlerActionAdapter" should "execute actions" in {

    val mockRequest = new MockHttpServletRequest("GET",helloUri)
    val mockResponse = new MockHttpServletResponse

    val modelAndView: ModelAndView = adapter.handle(mockRequest,mockResponse,helloAction)

    modelAndView.getViewName should be ("jsonTemplate")




  }




}
