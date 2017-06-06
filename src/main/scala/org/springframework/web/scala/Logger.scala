package org.springframework.web.scala

/**
  * Created by sam on 06/06/17.
  */
trait Logger {

  def logInfo(text:String):Unit = {
    println(text)
  }

}
