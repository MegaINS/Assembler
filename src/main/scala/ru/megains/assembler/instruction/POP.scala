package ru.megains.assembler.instruction

import ru.megains.assembler.{Memory, Register, Registers}

class POP(register: Register) extends Instruction {

    override def value(): Int = ???

    override def execute(): Unit = {
        register.value=  Memory.load(Registers.SP.value)
        Registers.SP.value =  (Registers.SP.value+ 1).toShort
    }
    override def toString: String = {
        s"POP $register"
    }

    override val OP: Int = 0x2    //0010

    override def OPCode(): Array[Int] = {
        val inst:Array[Int] = new Array[Int](length)

        inst(0) = (register.id << 12| 0xE << 8 | 0xE << 4 | (OP  + (length- 1)))
        inst
    }
}
