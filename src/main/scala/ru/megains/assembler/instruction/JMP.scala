package ru.megains.assembler.instruction

import ru.megains.assembler.{Registers, Token, TokenType}

class JMP(name:Token,val label:String) extends Instruction {


    var IP:Int = -1
    var OP_IP:Int = -1
    override def value(): Int = ???
    length = 2
    override def execute(): Unit = {
        name.tokenType match {
            case TokenType.JMP => Registers.IP.value = IP.toShort
            case TokenType.JE =>
                if((Registers.FLAGS.value & 1) == 1){
                    Registers.IP.value = IP.toShort
                }
            case TokenType.JNE =>
                if((Registers.FLAGS.value & 1) == 0){
                    Registers.IP.value = IP.toShort
                }
        }
    }

    override def toString: String = {
        s"${name.text} $label"
    }

    override val OP: Int = 0x6   // 0111 + 0110

    override def OPCode(): Array[Int] = {
        //todo

        val opJ = name.tokenType match {
            case TokenType.JMP => 0x0
            case TokenType.JE => 0x1
            case TokenType.JNE => 0x2
        }


        val inst:Array[Int] = new Array[Int](length)
        inst(0) = (opJ << 8 | Registers.$0.id << 4 | (OP  + (length- 1)))
        if(length == 2) inst(1) = OP_IP
        inst
    }
}
