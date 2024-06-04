package ru.megains.assembler.instruction

import ru.megains.assembler.{Registers, Token}

class CALL(val name: Token) extends Instruction {

    length = 2

    var IP:Int = -1
    var OP_IP:Int = -1

    override def value(): Int = ???

    override def execute(): Unit = {
        new PUSH(Registers.IP).execute()
        Registers.IP.value = IP.toShort
    }

    override def toString: String = {
        s"CALL ${name.text}"
    }

    override val OP: Int = 0xD  //1101

    override def OPCode(): Array[Int] = {
        val inst:Array[Int] = new Array[Int](length)
        inst(0) = (0xE << 12 | 0xD <<  8 | 0xE << 4 | (OP ))
        inst(1) = OP_IP
        inst
    }


}
