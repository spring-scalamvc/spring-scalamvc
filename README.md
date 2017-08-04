## Spring-ScalaMVC

### scala support for spring-mvc

    @Bean
     def hello() = {
        val requestToString: MvcRequest => String = r => r.params.getOrElse("name", "world")
        val formatMyString: String => String = rawString => rawString.capitalize
        val welcome: String => String = name => s"Welcome $name"

     Action(Get("/hello")) {
           requestToString andThen formatMyString andThen welcome
     }

  }