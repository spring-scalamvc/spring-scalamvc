package org.springframework.web

import java.io.File

import org.apache.catalina.Context
import org.apache.catalina.startup.Tomcat
import org.springframework.web.servlet.DispatcherServlet

object App {

  private var tomcat: Tomcat = null
  private var ctx: Context = null

  
  def main(args : Array[String]) {

    tomcat = new Tomcat()
    tomcat.setPort(8080)
    ctx = tomcat.addContext("", new File(".").getAbsolutePath())
    ctx.addParameter("contextConfigLocation", "classpath*:application-context.xml")
    ctx.addApplicationListener("org.springframework.web.context.ContextLoaderListener")
    val apiServlet = Tomcat.addServlet(ctx, "api", new DispatcherServlet())
    apiServlet.addInitParameter("contextConfigLocation", " classpath:api-servlet-context.xml")
    apiServlet.setAsyncSupported(true)
    ctx.addServletMapping("/api/*", "api")

    tomcat.start()
    tomcat.getServer().await()




  }

}
