package ru.megains.assembler

class Branch (a: Register, b: Register, operator: Operator,value:Short) extends Command {

    override val opCode: Short = 10


    override def execute(): Unit = {

    }

    override def getByteCode: (Short,Short) = (((b.id << 12) | (a.id << 8) | (value >> 12 << 4) | opCode ).toShort, (operator.code << 12 | value << 4 >> 4).toShort)


}
