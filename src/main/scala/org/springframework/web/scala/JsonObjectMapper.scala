package org.springframework.web.scala

import com.fasterxml.jackson.annotation.JsonInclude.Include
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

/**
  * Created by sam on 21/07/17.
  */
class JsonObjectMapper extends ObjectMapper with ScalaObjectMapper{

     registerModule(DefaultScalaModule)

     setSerializationInclusion(Include.NON_NULL)
}
