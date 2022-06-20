package ru.megains.assembler

class ADD(target:Register,a:Register,b:Register) extends Command {


    def this(a:Register,b:Register){
        this(a,a,b)
    }

    override def execute(): Unit = target.value = (a.value + b.value).toShort

    override def getByteCode: (Short,Short) = ???

    override val opCode: Short = 2
}


