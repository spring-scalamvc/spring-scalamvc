package org.springframework.web.app

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.scala.{Action, Get, MvcRequest}


@Configuration
class AppConfiguration {

  @Bean
  def hello() = {
    val requestToString: MvcRequest => String = r => r.params.getOrElse("name","world")
    val formatMyString : String => String = rawString => rawString.capitalize
    val welcome : String => String = name => s"Welcome $name"

    Action(Get( "/hello") ){
       requestToString andThen formatMyString andThen welcome
     }
  }

}
