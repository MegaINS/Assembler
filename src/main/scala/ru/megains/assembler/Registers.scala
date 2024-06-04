package ru.megains.assembler

import scala.collection.mutable

object Registers {

    val registerName = new mutable.HashMap[String,Register]()
    registerName += "$0" -> new Register0
    for (i<-1 to 12){
        registerName += "$"+ i -> new Register(i.toShort)
    }
    registerName += "IP" -> new Register(13)
    registerName += "SP" -> new Register(14)
    registerName += "FLAGS" -> new Register(15)
    val $0: Register = registerName("$0")
    val IP: Register = registerName("IP")
    val SP: Register = registerName("SP")
        SP.value = 1001

    val FLAGS:Register =  registerName("FLAGS")
    def apply(name:String): Register = registerName(name)
}
