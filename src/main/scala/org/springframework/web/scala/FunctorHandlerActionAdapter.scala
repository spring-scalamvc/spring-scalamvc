package org.springframework.web.scala

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.springframework.web.servlet.resource.ResourceHttpRequestHandler
import org.springframework.web.servlet.{HandlerAdapter, ModelAndView}


/**
  * Created by sam on 09/03/17.
  */
class FunctorHandlerActionAdapter  extends HandlerAdapter{


  val functor:FunctorHandler = new FunctorHandler()


  override def handle(httpServletRequest: HttpServletRequest, httpServletResponse: HttpServletResponse, o: scala.Any): ModelAndView = {

    o match {

      case action:Action[_,_] => {
        val input = action.req.defaultConverter()(httpServletRequest)

        val eval = functor.map(input,action.fn)

        val outModel = new ModelAndView("jsonTemplate")

        outModel.addObject("content",eval.read())

        //TODO manage response

        return outModel
      }
      case _ =>  {
        println("got a " + o.getClass.getCanonicalName)
        new ModelAndView("broken")
      }


    }

  //  val action:Action[AnyRef,AnyRef] = o.asInstanceOf[Action[AnyRef,AnyRef]]




  }

  override def getLastModified(httpServletRequest: HttpServletRequest, o: scala.Any): Long = -1L

  override def supports(o: scala.Any): Boolean =  true //o.isInstanceOf[FunctoidHandler[_,_]]
}
