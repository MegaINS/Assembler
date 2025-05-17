package ru.megains.assembler.instruction

import ru.megains.assembler.{Register, Registers}

class LWV(address: Register , variable:Variable ,target:Register) extends LW(address,1,target){


    def this(variable:Variable , target: Register) = {
        this(Registers.$0, variable, target: Register)

    }


    override def OPCode(): Array[Int] ={
        offset = variable.OP_IP
        super.OPCode()
    }

    override def execute(): Unit = {
        offset = variable.OP_IP
        super.execute()
    }
}
