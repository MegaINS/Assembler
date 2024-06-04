package ru.megains.assembler.instruction

import ru.megains.assembler.{Memory, Register, Registers}

class LW(address: Register , offset:Int ,target:Register) extends Instruction{

    length = if(offset > 0)  2 else 1
    override val OP: Int = 0xA    //1011 + 1010

    def this(offset:Int,target:Register){
        this(Registers.$0, offset:Int,target:Register)
    }

    def this(address: Register,target:Register){
        this(address, 0,target:Register)

    }

    override def value(): Int = -1
    override def OPCode():Array[Int] ={
        val inst:Array[Int] = new Array[Int](length)
        inst(0) = (target.id << 12 | address.id << 4 | (OP  + (length- 1)))
        if(length == 2) inst(1) = offset/2
        inst
    }
    override def execute(): Unit = {
        target.value = Memory.load((address.value + offset/2).toShort)
    }


    override def toString = s"LW $address + $offset -> $target"




}
