package ru.megains.assembler.instruction

import ru.megains.assembler.{Memory, Register, Registers}

class PUSH(register: Register) extends Instruction {

    override def value(): Int = ???

    override def execute(): Unit = {
        Registers.SP.value =  (Registers.SP.value - 1).toShort
        Memory.save(Registers.SP.value,register.value)

    }
    override def toString: String = {
        s"PUSH $register"
    }

    override val OP: Int = 0x4   //0100 + 0101

    override def OPCode(): Array[Int] = {
        //todo
        val inst:Array[Int] = new Array[Int](length)
        inst(0) = (0xE << 12 | register.id <<  8 | 0xE << 4 | (OP  + (length- 1)))
      //  if(length == 2) inst(1) = offset

        inst
    }
}
