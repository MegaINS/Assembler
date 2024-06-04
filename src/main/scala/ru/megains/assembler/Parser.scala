package ru.megains.assembler

import ru.megains.assembler.TokenType.TokenType
import ru.megains.assembler.instruction._

import scala.collection.mutable.ArrayBuffer

object Parser {

    var tokens: ArrayBuffer[Token] = _
    var pos = 0
    val instructions:ArrayBuffer[Instruction] = new ArrayBuffer[Instruction]()
    def setTokens(newTokens: ArrayBuffer[Token]): Unit ={
        tokens = newTokens
    }



    def parse(): ArrayBuffer[Instruction] = {
        var lastToken:Token = null
        while (get().tokenType != TokenType.EOF){
            val token = get()
            pos += 1
            token.tokenType match {
                case TokenType.ADD => parseADD()
                case TokenType.SUB => parseSUB()
                case TokenType.INC => parseINC()
                case TokenType.AND =>  parseAND()
                case TokenType.MOV => parseMOV()
                case TokenType.LW => parseLW()
                case TokenType.SW => parseSW()
                case TokenType.CALL => parseCALL()
                case TokenType.RET => parseRET()
                case TokenType.CMP => parseCMP()
                case TokenType.JMP | TokenType.JE | TokenType.JNE => parseJMP(token)
                case TokenType.PUSH => parsePUSH()
                case TokenType.POP => parsePOP()

                case TokenType.WORD | TokenType.DOT => parseLabel(token)

                case TokenType.SHUTDOWN => parseSHUTDOWN()


                case TokenType.REGISTER | TokenType.PLUS | TokenType.MINUS | TokenType.COLON | TokenType.AT  | TokenType.NUMBER =>
                    throw new RuntimeException("TokenType " + token.tokenType)

                case TokenType.EOL =>

                case TokenType.EOF =>
            }
            lastToken = token
        }
        instructions
    }

    private def parseSHUTDOWN() = {
        instructions += new SHUTDOWN()
    }

    private def parseAND(): Unit ={
        val target =  Registers(consume(TokenType.REGISTER).text)


        get().tokenType match {
            case TokenType.REGISTER =>
                val a =  Registers(consume(TokenType.REGISTER).text)
                if( get().tokenType ==TokenType.REGISTER ) {
                    instructions += new ALU(a, Registers(consume(TokenType.REGISTER).text),target,ALUOP.AND)

                }else{
                    instructions += new ALU(target,a,target,ALUOP.AND)
                }
            case TokenType.NUMBER =>
                instructions += new ALU(target ,consume(TokenType.NUMBER).text.toInt,target ,ALUOP.AND)
        }
    }

    private def parseINC()  : Unit ={
        val reg =  Registers(consume(TokenType.REGISTER).text)
        instructions += new ALU( reg, 1,reg,ALUOP.ADD)
    }

    private def parseSUB() : Unit ={
        val target =  Registers(consume(TokenType.REGISTER).text)


        get().tokenType match {
            case TokenType.REGISTER =>
                val a =  Registers(consume(TokenType.REGISTER).text)
                if( get().tokenType ==TokenType.REGISTER ) {
                    instructions += new ALU(a, Registers(consume(TokenType.REGISTER).text),target,ALUOP.SUB)

                }else{
                    instructions += new ALU(a,target,target,ALUOP.SUB)
                }
            case TokenType.NUMBER =>
                instructions += new ALU(target,consume(TokenType.NUMBER).text.toInt,target,ALUOP.SUB)
        }
    }
    private def parseADD()  : Unit ={

        val target =  Registers(consume(TokenType.REGISTER).text)

        get().tokenType match {
            case TokenType.REGISTER =>
                val a =  Registers(consume(TokenType.REGISTER).text)
               if( get().tokenType ==TokenType.REGISTER ) {
                   instructions += new ALU(a,Registers(consume(TokenType.REGISTER).text),target,ALUOP.ADD)
               }else{
                   instructions += new ALU(a,target,target,ALUOP.ADD)
               }
            case TokenType.NUMBER =>
                instructions += new ALU(target,consume(TokenType.NUMBER).text.toInt,target,ALUOP.ADD)
        }


    }

    private def parseRET() : Unit = {
        val offset = get().tokenType match {
            case TokenType.NUMBER =>
               consume(TokenType.NUMBER).text.toInt
            case _ => 0
        }
        instructions += new RET(offset)
    }

    private def parseCMP(): Unit = {

        val r1: Register = Registers(consume(TokenType.REGISTER).text)
        get().tokenType match {
            case TokenType.NUMBER =>
                val n1 = consume(TokenType.NUMBER).text.toInt
                instructions += new ALU(r1,n1,Registers.$0,ALUOP.SUB)
            case TokenType.REGISTER =>
                instructions += new ALU(r1,Registers(consume(TokenType.REGISTER).text),Registers.$0,ALUOP.SUB)

        }
    }
    private def parsePUSH(): Unit = {
        instructions += new PUSH( Registers(consume(TokenType.REGISTER).text))
    }
    private def parsePOP(): Unit = {
        instructions += new POP( Registers(consume(TokenType.REGISTER).text))
    }

    private def parseCALL() : Unit = {
        instructions += new CALL(consume(TokenType.WORD))
    }

    private def parseJMP(token: Token) : Unit = {
       if(get().tokenType == TokenType.DOT ) {
           consume(TokenType.DOT)
           instructions += new JMP(token,lastLabel + "."+consume(TokenType.WORD).text)
       }else {
           instructions += new JMP(token,consume(TokenType.WORD).text)
       }
    }

    var lastLabel:String = ""
    private def parseLabel(token: Token): Unit ={
        if(token.tokenType == TokenType.DOT){
            instructions += new Label(lastLabel +"."+consume(TokenType.WORD).text)
        }else{
            lastLabel = token.text
            instructions += new Label(token.text)
        }
        consume(TokenType.COLON)

    }




    def parseLW(): Unit ={
        get().tokenType match {
            case ru.megains.assembler.TokenType.LBRACKET =>
                consume(TokenType.LBRACKET)
                val r1: Register =  Registers(consume(TokenType.SP).text)
                var offset =  get().tokenType match {
                    case TokenType.PLUS =>
                        consume(TokenType.PLUS)
                        1
                    case TokenType.MINUS =>
                        consume(TokenType.MINUS)
                        -1
                }
                offset *= consume(TokenType.NUMBER).text.toInt

                consume(TokenType.RBRACKET)
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new LW(r1,offset, r2)
            case TokenType.REGISTER =>
                val r1: Register = Registers(consume(TokenType.REGISTER).text)
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new LW(r1, r2)
        }


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
                instructions += new ALU(Registers.$0,n1,r2,ALUOP.ADD)
            case TokenType.REGISTER =>
                val r1 = Registers(consume(TokenType.REGISTER).text)
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions +=new ALU(Registers.$0,r1,r2,ALUOP.ADD)
        }



    }
    def get(index:Int = 0): Token ={
        tokens(pos+index)
    }

    def consume(tokenType: TokenType): Token = {
        val current = get()
        if (tokenType != current.tokenType) {
            throw new RuntimeException("Token " + current + " doesn't match " + tokenType)
        }
        pos += 1
        current
    }

}
