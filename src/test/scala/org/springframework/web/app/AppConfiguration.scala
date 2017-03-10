package org.springframework.web.app

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.scala.{Action, Get, MvcRequest}


@Configuration
class AppConfiguration {

  @Bean
  def hello():Action[MvcRequest,String] = {
    val requestToString: MvcRequest => String = r => r.params.getOrElse("nome","world")
    val formatMyString : String => String = in => in.capitalize
    val welcome : String => String = in => s"Welcome $in"
    new Action[MvcRequest,String](Get("/hello"),requestToString andThen formatMyString andThen welcome )
  }

}
