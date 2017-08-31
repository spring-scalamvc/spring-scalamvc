package org.springframework.web.scala

import javax.servlet.http.HttpServletRequest

import scala.util.{Success, Try, Failure}


trait CommandValidator[A] {

  def validate(commandParam:A):Try[A]


}

trait CommandExecutor[A] {
  def execute(commandParam:A):Unit
}




class Command[A](queryPath:String,validator:CommandValidator[A],executor:CommandExecutor[A])(implicit whatever: Manifest[A]) {

  def xps(c:A):String = validator.validate(c) match {
    case Success(aValue) => {
      executor.execute(aValue)
      "ok"
    }
    case Failure(fData) => {
          fData.toString
    }
  }


  def processCommand: A => String =   xps


  val action = Action(Post[A](queryPath)) {

  processCommand

  }


}

object Command {

  def apply[A](queryPath: String, validator: CommandValidator[A], executor: CommandExecutor[A])(implicit whatever: Manifest[A]): Command[A] = new Command(queryPath, validator, executor)


  def apply[A](queryPath: String, validatorFn: A => Try[A] , executorFn:  A => Unit)(implicit whatever: Manifest[A]): Command[A] = new Command(queryPath, new CommandValidator[A] {
    override def validate(commandParam: A): Try[A] = validatorFn(commandParam)
  }, new CommandExecutor[A] {
    override def execute(commandParam: A): Unit = executorFn(commandParam)
  })




}
