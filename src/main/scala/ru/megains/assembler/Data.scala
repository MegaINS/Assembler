package ru.megains.assembler

import ru.megains.assembler.instruction.Variable

import scala.collection.mutable

class Data {
    
    val variable:mutable.HashMap[String,Variable] = new mutable.HashMap[String,Variable]()

}
