package ru.megains.assembler.instruction

import ru.megains.assembler.Registers

class RET(offset:Int) extends Instruction {

    override def value(): Int = ???

    override def execute(): Unit = {

        new POP(Registers.IP).execute()
        Registers.SP.value =  (Registers.SP.value + offset/2).toShort
    }

    override def toString = s"RET"

    override val OP: Int = 0xE  //1110

    override def OPCode(): Array[Int] = {
        val inst:Array[Int] = new Array[Int](length)
        inst(0) = (((offset/2)+1) << 12 | 0xD <<  8 | 0xE << 4 | (OP  + (length- 1)))
        inst
    }
}
