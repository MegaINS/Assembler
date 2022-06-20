package ru.megains.assembler

import ru.megains.assembler.TokenType.TokenType
import ru.megains.assembler.instruction.{Instruction, LW, MOV, SW}

import scala.collection.mutable.ArrayBuffer

object Parser {

    var tokens: ArrayBuffer[Token] = _
    var pos = 0
    val instructions:ArrayBuffer[Instruction] = new ArrayBuffer[Instruction]()
    def setTokens(newTokens: ArrayBuffer[Token]): Unit ={
        tokens = newTokens
    }



    def parse(): ArrayBuffer[Instruction] = {

        while (get().tokenType != TokenType.EOF){
            val token = get()
            pos += 1
            token.tokenType match {
                case TokenType.ADD => 
                case TokenType.SUB => 
                case TokenType.INC => 
                case TokenType.AND => 
                case TokenType.MOV => parseMOV()
                case TokenType.LW => parseLW()
                case TokenType.SW => parseSW()
                case TokenType.CALL => 
                case TokenType.RET => 
                case TokenType.CMP => 
                case TokenType.JMP => 
                case TokenType.JE => 
                case TokenType.JNE => 
                case TokenType.PUSH => 
                case TokenType.POP => 
                case TokenType.EOL => 
                case TokenType.NUMBER => 
                case TokenType.EOF =>
                case TokenType.WORD => 
                case TokenType.REGISTER => 
                case TokenType.PLUS => 
                case TokenType.MINUS => 
                case TokenType.COLON => 
                case TokenType.AT =>
                case TokenType.UNDERSCORE =>
            }
        }
        instructions
    }


    def parseLW(): Unit ={
        val r1: Register = Registers(consume(TokenType.REGISTER).text)
        val r2: Register = Registers(consume(TokenType.REGISTER).text)
        instructions += new LW(r1, r2)

    }
    def parseSW(): Unit ={
        val r1: Register = Registers(consume(TokenType.REGISTER).text)
        val r2: Register = Registers(consume(TokenType.REGISTER).text)
        instructions += new SW(r1, r2)

    }
    def parseMOV(): Unit ={
      get().tokenType match {
            case TokenType.NUMBER =>
                val n1 = consume(TokenType.NUMBER).text.toInt
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new MOV(n1, r2)
            case TokenType.REGISTER =>
                val r1 = Registers(consume(TokenType.REGISTER).text)
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new MOV(r1, r2)
        }



    }
    def get(index:Int = 0): Token ={
        tokens(pos+index)
    }

    def consume(tokenType: TokenType): Token = {
        val current = get()
        if (tokenType != current.tokenType) throw new RuntimeException("Token " + current + " doesn't match " + tokenType)
        pos += 1
        current
    }

    //    def parse(data: Array[String]): Command = {
    //        data(0) match {
    //            case value: String if Operator.contains(value) => new OperationALU(Registers(data(1)), Registers(data(2)), Registers(data(3)), Operator.get(value))
    //            case "lw" =>
    //                if (data.length == 3) {
    //                    new Load(Registers(data(1)), data(2).toShort)
    //                } else {
    //                    new Load(Registers(data(1)), Registers(data(2)), data(3).toShort)
    //                }
    //            case "sw" =>
    //                if (data.length == 3) {
    //                    new Save(Registers(data(1)), data(2).toShort)
    //                } else {
    //                    new Save(Registers(data(1)), Registers(data(2)), data(3).toShort)
    //                }
    //
    //        }
    //    }
}
