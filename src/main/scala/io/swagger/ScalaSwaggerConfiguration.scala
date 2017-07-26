package io.swagger

import io.swagger.models.Swagger
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.web.scala.{Action, Get, MvcRequest}
import org.springframework.web.servlet.{HandlerMapping, ViewResolver}
import org.springframework.web.servlet.config.annotation.{EnableWebMvc, WebMvcConfigurerAdapter}
import org.springframework.web.servlet.view.InternalResourceViewResolver

/**
  * Created by sam on 20/06/17.
  */

@Configuration
@EnableWebMvc
class ScalaSwaggerConfiguration extends WebMvcConfigurerAdapter {


  @Bean
  def swaggerActionProcessor():SwaggerActionProcessor = {
    new SwaggerActionProcessor
  }

  @Bean
  def apiDocs() = {

    val fun: MvcRequest => Swagger = r => swaggerActionProcessor().swagger


    Action(Get("/v2/api-docs")) {

      fun
    }

  }







}
