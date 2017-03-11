package org.springframework.web.scala

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.springframework.context.{ApplicationContext, ApplicationContextAware}
import org.springframework.context.annotation._
import org.springframework.web.servlet.{HandlerAdapter, View, ViewResolver}
import org.springframework.web.servlet.view.BeanNameViewResolver
import org.springframework.web.servlet.view.json.MappingJackson2JsonView

import scala.collection.JavaConverters._

/**
  * Created by sam on 09/03/17.
  */
@Configuration
class MvcConfiguration  extends ApplicationContextAware{

  var appContext:ApplicationContext = null


  override def setApplicationContext(applicationContext: ApplicationContext): Unit = {

   appContext = applicationContext;

  }

  @Bean
  def  jsonTemplate():View = {
    val view:MappingJackson2JsonView = new MappingJackson2JsonView()
    view.setPrettyPrint(true)
   // view.setExtractValueFromSingleKeyModel(true);
    return view
  }

  @Bean
  def  viewResolver():ViewResolver = {
    return new BeanNameViewResolver()
  }


  @Bean
  def handlerMapping():ActionHandlerMapping = {

    println("handler! ")

    new ActionHandlerMapping(appContext.getBeansOfType(classOf[Action[String,String]]).values.asScala.map(action => {
      (action.req.path,action)
    }
    ).toMap)
  }

  @Bean
  def  handlerAdapter():HandlerAdapter = {
    new FunctorHandlerActionAdapter
  }





}
