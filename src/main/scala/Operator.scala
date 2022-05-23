package ru.megains.assembler

import scala.collection.immutable.HashMap
import scala.collection.mutable

sealed class Operator(val name: String, val code: Short, val func: (Short, Short) => Short) {


}

object Operator {

    case object ADD extends Operator("add", 0, (a, b) => (a + b).toShort)

    case object SUB extends Operator("sub", 1, (a, b) => (a - b).toShort)

    case object == extends Operator("==", 5, (a, b) => if (a == b) 1 else 0)

    case object != extends Operator("!=", 6, (a, b) => if (a != b) 1 else 0)


    val OPERATOR_BY_NAME: mutable.HashMap[String, Operator] = new mutable.HashMap[String, Operator]()

    OPERATOR_BY_NAME += ADD.name -> ADD
    OPERATOR_BY_NAME += SUB.name -> SUB
    OPERATOR_BY_NAME += ==.name -> ==
    OPERATOR_BY_NAME += !=.name -> !=

    def get(value: String): Operator = {
        OPERATOR_BY_NAME(value)
    }

    def contains(value: String): Boolean = {
        OPERATOR_BY_NAME.contains(value)
    }

}
