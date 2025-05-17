package ru.megains.assembler.instruction

import ru.megains.assembler.{Memory, Register, Registers}

class SW (target: Register ,var offset:Int ,address:Register) extends Instruction{

    length = if (offset > 0) 2 else 1
    def this(offset:Int,target:Register)={
        this(Registers.$0, offset:Int,target:Register)
    }

    def this(address: Register,target:Register)={
        this(address, 0,target:Register)
    }

    override def value(): Int = -1

    override def execute(): Unit = {
        Memory.save((address.value + offset).toShort,target.value)
    }

    override def toString = s"SW $address + $offset -> $target"

    override val OP: Int = 0x8 //1001 + 1000

    override def OPCode(): Array[Int] = {
        val inst:Array[Int] = new Array[Int](length)
        inst(0) = (target.id << 8 | address.id << 4 | (OP  + (length- 1)))
        if(length == 2) inst(1) = offset
        inst
    }
}
