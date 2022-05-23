package ru.megains.assembler

class OperationALU(target: Register, a: Register, b: Register, operationType: Operator) extends Command {

    override val opCode: Short = 2

    def this(a: Register, b: Register, operationType: Operator) {
        this(a, a, b, operationType)
    }

    override def execute(): Unit = target.value = operationType.func(a.value, b.value)

    override def getByteCode: (Short,Short) = (((b.id << 12) | (a.id << 8) | (target.id << 4) | opCode ).toShort, (operationType.code << 12).toShort)


}
