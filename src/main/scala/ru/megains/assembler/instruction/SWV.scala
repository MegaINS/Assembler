package ru.megains.assembler.instruction

import ru.megains.assembler.{Register, Registers}

class SWV(target: Register , variable:Variable ,address:Register) extends SW(target: Register ,1,address:Register){
    
    
    def this(target: Register,variable: Variable) = {
        this(target: Register,variable: Variable,Registers.$0)
    }

    override def OPCode(): Array[Int] = {
        offset = variable.OP_IP
        super.OPCode()
    }

    override def execute(): Unit = {
        offset = variable.OP_IP
        super.execute()
    }
}
