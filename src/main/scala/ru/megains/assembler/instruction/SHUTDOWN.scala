package ru.megains.assembler.instruction

import ru.megains.assembler.Executor

class SHUTDOWN extends Instruction {

    override def value(): Int = ???

    override def execute(): Unit = {
        Executor.run = false
    }


    override def toString = s"SHUTDOWN"

    override val OP: Int = 0xf //1111

    override def OPCode(): Array[Int] = {
        val inst:Array[Int] = new Array[Int](length)
        inst(0) = OP
        inst
    }
}
