package org.springframework.web.scala

import scala.util.{Failure, Success, Try}

trait FilterValidator[A] {

  def validate(commandParam:A):Try[A]

}

trait QueryExecutor[F,Q] {

  def execute(filter:F):List[Q]

}




class Query[F,Q](queryPath:String,validator:FilterValidator[F],executor:QueryExecutor[F,Q])(implicit whatever: Manifest[F]) {

  def processQuery(c:F):List[Q]  = validator.validate(c) match {
    case Success(aValue) => {
      executor.execute(aValue)
    }
    case Failure(fData) => {
     List()
    }
  }

  val requestToFilters: MvcRequest => F = r => whatever.erasure.newInstance.asInstanceOf[F]


  val action = Action(Get(queryPath)) {

    requestToFilters andThen  processQuery

  }

}


class EmptyFilter {}

class EmptyFilterValidator extends FilterValidator[EmptyFilter] {
  override def validate(commandParam: EmptyFilter) = Success(commandParam)
}

trait SimpleQueryExecutor[Q] extends QueryExecutor[EmptyFilter,Q]{

  override def execute(filter: EmptyFilter) :List[Q] = execute()

  def execute():List[Q]
}





object Query {
  def apply[F,Q](queryPath: String, validator: FilterValidator[F], executor: QueryExecutor[F, Q])(implicit whatever: Manifest[F]): Query[F,Q]
  = new Query[F, Q](queryPath, validator, executor)

  def apply[Q](queryPath: String, executor:SimpleQueryExecutor[Q])(implicit whatever: Manifest[EmptyFilter]): Query[EmptyFilter,Q]
  = new Query[EmptyFilter, Q](queryPath, new EmptyFilterValidator, executor)



  /*class SingleParameter[S](value:S)

  class SingleParameterVallidator[F](validateFn:F => Try[F]) extends FilterValidator[F] {
    override def validate(commandParam: SingleParameter[F]) = validateFn(commandParam.v)
  }

  def apply[F,Q](queryPath: String, validateFn:F => Try[SingleParameter[F]], executor: QueryExecutor[SingleParameter[F],Q])(implicit whatever: Manifest[SingleParameter[F]]): Query[EmptyFilter,Q]
  = new Query[F, Q](queryPath, new SingleParameterVallidator(validateFn), executor)
  */

}