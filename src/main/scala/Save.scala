package ru.megains.assembler

class Save(target:Register, a:Register, value:Short) extends Command {


    def this(target:Register,value:Short){
        this(target:Register,Registers.$0,value:Short)
    }

    override val opCode: Short = 5

    override def execute(): Unit = {

    }

    override def getByteCode: (Short, Short) = (( target.id << 12 | a.id << 8 | opCode).toShort, value)
}
