package org.springframework.web.scala

import javax.servlet.http.HttpServletRequest

import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping
import org.springframework.web.servlet.{HandlerExecutionChain, HandlerMapping}

/**
  * Created by sam on 09/03/17.
  */
class ActionHandlerMapping(handlers:Map[String,Action[_,_]]) extends AbstractUrlHandlerMapping{



  override def getHandlerInternal(request: HttpServletRequest): AnyRef = {
    val lookupPath = this.getUrlPathHelper.getLookupPathForRequest(request)
    println("lookupPath: " + lookupPath)
    handlers.get(lookupPath) match {
      case None => throw new RuntimeException( lookupPath + " not found in mapping ") // todo 404
      case Some(value) => value
    }

  }
}
