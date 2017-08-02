package example.app

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import io.swagger.models.Swagger
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.scala.{Action, Get, MvcRequest, Post}

case class Person(name:String)


@Configuration
class AppConfiguration {




  @Bean
  def hello() = {
    val requestToString: MvcRequest => String = r => r.params.getOrElse("name", "world")
    val formatMyString: String => String = rawString => rawString.capitalize
    val welcome: String => String = name => s"Welcome $name"

    Action(Get("/hello")) {
      requestToString andThen formatMyString andThen welcome
    }

  }

  @Bean
  def echo() = {
    val display: Person => Person = p => {
      val mapper = new ObjectMapper() with ScalaObjectMapper
      mapper.registerModule(DefaultScalaModule)
      println(mapper.writeValueAsString(p))
      p
    }

    Action(Post[Person]("/echo")) {

      display

    }

  }




}