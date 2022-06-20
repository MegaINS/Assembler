package ru.megains.assembler.instruction

import ru.megains.assembler.{Register, Registers}

class SW (address: Register , offset:Int ,target:Register) extends Instruction{


    def this(offset:Int,target:Register){
        this(Registers.$0, offset:Int,target:Register)
    }

    def this(address: Register,target:Register){
        this(address, 0,target:Register)
    }

    override def value(): Int = -1

    override def execute(): Unit = {}

    override def toString = s"SW $address + $offset -> $target"
}
