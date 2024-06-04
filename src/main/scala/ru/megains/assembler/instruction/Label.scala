package ru.megains.assembler.instruction

class Label ( val name:String) extends Instruction{


    var IP: Int = -1
    var OP_IP: Int = -1
    override def value(): Int = ???

    override def execute(): Unit = {

    }

    override def toString: String = {
        s"Label $name"
    }

    override val OP: Int = -1

    override def OPCode(): Array[Int] = {
        val inst:Array[Int] = new Array[Int](length)
        inst
    }


}
