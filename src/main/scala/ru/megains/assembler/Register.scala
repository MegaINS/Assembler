package ru.megains.assembler

class Register(val id:Short) {
    
    var value:Short = 0


    override def toString: String = "$"+id
}
