package ru.megains.assembler.instruction

import ru.megains.assembler.{Register, Registers}

class MOV(address: Register,target:Register) extends Instruction{

    var number = 0
    var rr = true

    def this(value:Int,target:Register){
        this(Registers.$0,target:Register)
        number = value
        rr = false
    }


    override def value(): Int = -1

    override def execute(): Unit = {
        if(rr){
            target.value = address.value
        }else{
            target.value = number.toShort
        }
    }

    override def toString: String = {
        if(rr){
            s"MOV $address -> $target"
        }else{
            s"MOV $number -> $target"
        }

    }

    override val OP: Int = -1

    override def OPCode(): Array[Int] = {
        val inst:Array[Int] = new Array[Int](length)
        inst
    }
}