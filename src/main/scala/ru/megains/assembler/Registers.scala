package ru.megains.assembler

import scala.collection.mutable

object Registers {

    val registerName = new mutable.HashMap[String,Register]()

    for (i<-0 to 31){
        registerName += "$"+ i -> new Register(i.toShort)
    }


    val $0: Register = registerName("$0")

    def apply(name:String): Register = registerName(name)
}
