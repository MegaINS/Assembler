package ru.megains.assembler.instruction

class Variable(name:String,val number:Int) extends Label(name){


    override def OPCode(): Array[Int] = {
        val inst: Array[Int] = new Array[Int](length)
        inst(0) = number
        inst
    }
}
