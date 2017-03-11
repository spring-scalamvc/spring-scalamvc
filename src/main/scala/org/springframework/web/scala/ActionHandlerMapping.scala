package org.springframework.web.scala

import javax.servlet.http.HttpServletRequest

import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping
import org.springframework.web.servlet.{HandlerExecutionChain, HandlerMapping}
import scala.collection.JavaConversions.mapAsScalaMap

/**
  * Created by sam on 09/03/17.
  */
class ActionHandlerMapping(handlers:Map[String,Action[_,_]]) extends AbstractUrlHandlerMapping{



  override def getHandlerInternal(request: HttpServletRequest): AnyRef = {
    val lookupPath = this.getUrlPathHelper.getLookupPathForRequest(request)
    println("lookupPath: " + lookupPath)

    handlers.get(lookupPath)  match {
      case None =>  lookupParametricPatters(lookupPath,request) match {
        case None => throw new RuntimeException( lookupPath + " not found in mapping ") // todo 404
        case Some(value) =>   {
          val vars:Map[String,String] = this.getPathMatcher().extractUriTemplateVariables(value.toString, lookupPath).toMap
          //println(vars)
        //  vars.foreach(k => println(k._1.toString +  " " + k._2.toString))
          vars.foreach(k => request.setAttribute(k._1.toString , k._2.toString) )


          return handlers.get(value).get
        }

      }
      case Some(value) => value
    }

  }
    def jkv(s: String, a: Any) = s -> a.asInstanceOf[AnyRef]


 /* def extractPathVariables(request: HttpServletRequest):Map[String,String] = {
    val lookupPath = this.getUrlPathHelper.getLookupPathForRequest(request)
    this.getPathMatcher().
      extractUriTemplateVariables(lookupParametricPatters(lookupPath,request).getOrElse("")
        ,lookupPath).

  }*/


  def lookupParametricPatters( urlPath:String,  request:HttpServletRequest):Option[String] = {



    val patternComparator1 = this.getPathMatcher().getPatternComparator(urlPath)
    val matchingPatterns = handlers.map( k => {
      val patternComparator: String = k._1
      if(this.getPathMatcher().`match`(patternComparator, urlPath))
          patternComparator
      else if (this.useTrailingSlashMatch() && !patternComparator.endsWith("/") && this.getPathMatcher().`match`(patternComparator + "/", urlPath))
          patternComparator + "/"

    }).toList.map(_.toString).sortWith( (k ,y)=>  patternComparator1.compare(k,y) > 0 )

    val bestMatch = matchingPatterns.headOption

    for{exactMatch <- bestMatch ;
        hendler <- handlers.get(exactMatch)}

      yield exactMatch






  }
}
