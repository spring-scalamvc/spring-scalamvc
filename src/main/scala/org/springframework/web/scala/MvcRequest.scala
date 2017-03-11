package org.springframework.web.scala

import javax.servlet.http.HttpServletRequest
import scala.collection.JavaConversions._


class MvcRequest(originalRequest:HttpServletRequest) {

  val parameters = originalRequest.getParameterMap.toMap.map(k => k._1.toString -> k._2.asInstanceOf[Array[String]].toSeq)
  val params = parameters.map(k => k._1 -> k._2.head)
  val headers = originalRequest.getHeaderNames.toSeq.map(header => header.toString -> originalRequest.getHeader(header.toString)).toMap
  val attributes = originalRequest.getAttributeNames.toSeq.map(attr => attr.toString -> originalRequest.getAttribute(attr.toString).toString).toMap


}
