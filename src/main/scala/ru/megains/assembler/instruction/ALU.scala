package ru.megains.assembler.instruction

import ru.megains.assembler.{Register, Registers}
import ALUOP.ALUOP

class ALU(a: Register, b: Register, c: Register, const: Int, aluOP: ALUOP) extends Instruction {

    override val OP: Int = 0x1 //0011 + 0001

    length = 2
    var i = 0

    def isI: Boolean = i == 2

    def this(a: Register, b: Register, c: Register, ALUOP: ALUOP) ={
        this(a: Register, b: Register, c: Register, 0, ALUOP: ALUOP)
    }

    def this(a: Register, const: Int, c: Register, ALUOP: ALUOP) ={
        this(a: Register, Registers.$0, c: Register, const, ALUOP: ALUOP)
        i = 2
    }

    override def value(): Int = ???

    override def OPCode(): Array[Int] = {
        val inst: Array[Int] = new Array[Int](length)
        if(isI){
            inst(0) = (c.id << 12 | (aluOP.id) << 8 | a.id << 4 | (OP + i))
            inst(1) = const
        }else{
            inst(0) = (c.id << 12 | (b.id) << 8 | a.id << 4 | (OP))
            inst(1) = aluOP.id << 12
        }

        inst
    }

    override def execute(): Unit = {

        aluOP match {
            case ALUOP.ADD =>
                if (isI) {
                    c.value = (a.value + const).toShort
                } else {
                    c.value = (a.value + b.value).toShort
                }
            case ALUOP.ADDC => ???
            case ALUOP.SUB =>
                if (isI) {
                    c.value = (a.value - const).toShort
                } else {
                    c.value = (a.value - b.value).toShort
                }
            case ALUOP.SUBC => ???
            case ALUOP.AND =>
                if (isI) {
                    c.value = (a.value & const).toShort
                } else {
                    c.value = (a.value & b.value).toShort
                }
            case ALUOP.OR =>
                if (isI) {
                    (a.value | const).toShort
                } else {
                    (a.value | b.value).toShort
                }
            case ALUOP.XOR =>
                if (isI) {
                    (a.value ^ const).toShort
                } else {
                    (a.value ^ b.value).toShort
                }
            case ALUOP.NOT => ???
            case ALUOP.LSR => ???
            case ALUOP.LSL =>
                if (isI) {
                    (a.value << const).toShort
                } else {
                    (a.value << b.value).toShort
                }
            case ALUOP.>>> => ???
        }

        if(isI){
            if(a.value == const){
                Registers.FLAGS.value = (Registers.FLAGS.value | 1).toShort
            }else{
                Registers.FLAGS.value = (Registers.FLAGS.value & 65534).toShort
            }
        }else{
            if(a.value == b.value){
                Registers.FLAGS.value = (Registers.FLAGS.value | 1).toShort
            }else{
                Registers.FLAGS.value = (Registers.FLAGS.value & 65534).toShort
            }

        }

    }

    override def toString: String = {
        "ALU $"+ s"${c.id} = " + "$"+ s"${a.id} " + aluOP.toString + (if(isI) s" ${const}" else " $" + s"${b.id}")

    }
}
