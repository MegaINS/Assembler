package ru.megains.assembler

object Parser {


    def parse(data: Array[String]): Command = {
        data(0) match {
            case value: String if Operator.contains(value) => new OperationALU(Registers(data(1)), Registers(data(2)), Registers(data(3)), Operator.get(value))
            case "lw" =>
                if (data.length == 3) {
                    new Load(Registers(data(1)), data(2).toShort)
                } else {
                    new Load(Registers(data(1)), Registers(data(2)), data(3).toShort)
                }
            case "sw" =>
                if (data.length == 3) {
                    new Save(Registers(data(1)), data(2).toShort)
                } else {
                    new Save(Registers(data(1)), Registers(data(2)), data(3).toShort)
                }

        }
    }
}
