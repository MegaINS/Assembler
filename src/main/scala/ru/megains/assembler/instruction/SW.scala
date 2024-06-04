package ru.megains.assembler.instruction

import ru.megains.assembler.{Memory, Register, Registers}

class SW (target: Register , offset:Int ,address:Register) extends Instruction{


    def this(offset:Int,target:Register){
        this(Registers.$0, offset:Int,target:Register)
        length = 2
    }

    def this(address: Register,target:Register){
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
        if(length == 2) inst(1) = offset/2
        inst
    }
}
