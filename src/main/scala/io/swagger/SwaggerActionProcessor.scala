package io.swagger

import example.app.Person
import io.swagger.models.parameters.Parameter
import io.swagger.models._
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.web.scala.Action

/**
  * Created by sam on 20/06/17.
  */
class SwaggerActionProcessor extends BeanPostProcessor {

  private val empty = ""
  val swagger:Swagger = new Swagger().info(new Info().title("title X").description("description Y")
    .version("1.0.0").termsOfService(empty).contact(new Contact().email(empty).name(empty).url(empty))
    .license(new License().name(empty).url(empty)))

  override def postProcessAfterInitialization(o: java.lang.Object, s: String): java.lang.Object = {




    val isAction = o.isInstanceOf[Action[_, _]]

    if(isAction){
      println("examin " + s)
      val a:Action[_,_] = o.asInstanceOf[Action[_,_]]
      if(!a.req.path.equals("/v2/api-docs")){
        swagger.setBasePath("/api")
        val path:Path = new Path
        val operation = new Operation
        operation.addConsumes("application/json")
        operation.addProduces("application/json")
        operation.description(" todo ... ")
        val response = new Response
        response.description("sample response")
        val output = a.sampleInput()
        println(output)
        response.example(" example.app.Person",output)
        operation.defaultResponse(response)
        path.set(a.req.method.toLowerCase,operation)
        swagger.path(a.req.path,path)
      }

    }

    return o
  }

  override def postProcessBeforeInitialization(o: java.lang.Object, s: String): java.lang.Object = o
}
