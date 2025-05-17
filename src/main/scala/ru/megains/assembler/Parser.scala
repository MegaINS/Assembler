package ru.megains.assembler

import ru.megains.assembler.SectionType.NONE
import ru.megains.assembler.TokenType
import ru.megains.assembler.TokenType.TokenType
import ru.megains.assembler.instruction.*

import scala.collection.mutable.ArrayBuffer

object Parser {

    var tokens: ArrayBuffer[Token] = _
    var pos = 0
    val instructions: ArrayBuffer[Instruction] = new ArrayBuffer[Instruction]()

    var section: SectionType = NONE
    val set:Set = new Set()
    val data: Data = new Data()
    var line = 1

    def setTokens(newTokens: ArrayBuffer[Token]): Unit = {
        tokens = newTokens
    }


    def parse(): ArrayBuffer[Instruction] = {
      //  var lastToken: Token = null
        while (get().tokenType != TokenType.EOF) {
            val token = next()
            token.tokenType match {
                case TokenType.SLASH =>{
                    consume(TokenType.SLASH)
                    while (next().tokenType!=TokenType.EOL){

                    }
                    line +=1
                }

                case TokenType.DATA | TokenType.SET | TokenType.CODE => {
                    consume(TokenType.COLON)
                    section = SectionType.values.find(_.tokenType == token.tokenType).get
                }
                case TokenType.EOL => line +=1
                case _ => {
                    section match {
                        case SectionType.CODE => parseCode(token)
                        case SectionType.SET => parseSet(token)
                        case SectionType.DATA => parseData(token)
                        case SectionType.NONE => throw new RuntimeException("SectionType.NONE" + token.tokenType)
                    }
                }
            }




          //  lastToken = token
        }
        instructions
    }

    def parseSet(token: Token): Unit = {
        token.tokenType match {
            case TokenType.WORD =>
                val token2 = next()
                val number = token2.tokenType match{
                    case TokenType.NUMBER => token2.text.toInt
                    case TokenType.HEX_NUMBER => Integer.parseInt(token2.text,16)
                    case _=> throw new RuntimeException("TokenType not Set " + token.tokenType)
                }
                token.text match
                    case "org" =>
                        set.org = number

            case TokenType.EOL => line +=1
            case _ => throw new RuntimeException("TokenType not Set " + token.tokenType)
        }
    }

    def parseData(token: Token): Unit = {
        token.tokenType match {
            case TokenType.WORD => {
                consume(TokenType.WORD)
                val token2 = next()
                val number = token2.tokenType match {
                    case TokenType.NUMBER => token2.text.toInt
                    case TokenType.HEX_NUMBER => Integer.parseInt(token2.text, 16)
                    case _ => throw new RuntimeException("TokenType not Data " + token.tokenType)
                }
                data.variable += token.text -> new Variable(token.text,number)
            }

            case TokenType.EOL => line +=1
            case _ => throw new RuntimeException("TokenType not Data " + token.tokenType)
        }
    }


    def parseCode(token:Token): Unit = {
        token.tokenType match {
            case TokenType.ADD => parseADD()
            case TokenType.SUB => parseSUB()
            case TokenType.INC => parseINC()
            case TokenType.DEC => parseDEC()

            case TokenType.AND => parseAND()
            case TokenType.OR => parseOR()
            case TokenType.XOR => parseXOR()
            case TokenType.MOV => parseMOV()
            case TokenType.LSL => parseLSL()
            case TokenType.LSR => parseLSR()
                
            case TokenType.CSL => parseCSL()
                
            case TokenType.LW => parseLW()
            case TokenType.SW => parseSW()
            case TokenType.CALL => parseCALL()
            case TokenType.RET => parseRET()
            case TokenType.CMP => parseCMP()
            case TokenType.JMP | TokenType.JE | TokenType.JNE | TokenType.JB | TokenType.JBE| TokenType.JG | TokenType.JGE=> parseJMP(token)
            case TokenType.PUSH => parsePUSH()
            case TokenType.POP => parsePOP()

            case TokenType.START |  TokenType.WORD | TokenType.DOT => parseLabel(token)

            case TokenType.SHUTDOWN |TokenType.END  => parseSHUTDOWN()


            case TokenType.REGISTER | TokenType.PLUS | TokenType.MINUS | TokenType.COLON | TokenType.AT | TokenType.NUMBER =>
                throw new RuntimeException("Line "+line+" TokenType " + token.tokenType)

            case TokenType.EOL => line +=1
            case TokenType.EOF =>
        }
    }

    private def parseSHUTDOWN() = {
        instructions += new SHUTDOWN()
    }

    private def parseXOR(): Unit = {
        val target = Registers(consume(TokenType.REGISTER).text)


        get().tokenType match {
            case TokenType.REGISTER =>
                val a = Registers(consume(TokenType.REGISTER).text)
                if (get().tokenType == TokenType.REGISTER) {
                    instructions += new ALU(a, Registers(consume(TokenType.REGISTER).text), target, ALUOP.XOR)

                } else {
                    instructions += new ALU(target, a, target, ALUOP.XOR)
                }
            case TokenType.NUMBER =>
                instructions += new ALU(target, consume(TokenType.NUMBER).text.toInt, target, ALUOP.XOR)
            case TokenType.HEX_NUMBER =>
                instructions += new ALU(target, Integer.parseInt(consume(TokenType.HEX_NUMBER).text, 16), target, ALUOP.XOR)
        }
    }


    private def parseOR(): Unit = {
        val target = Registers(consume(TokenType.REGISTER).text)


        get().tokenType match {
            case TokenType.REGISTER =>
                val a = Registers(consume(TokenType.REGISTER).text)
                if (get().tokenType == TokenType.REGISTER) {
                    instructions += new ALU(a, Registers(consume(TokenType.REGISTER).text), target, ALUOP.OR)

                } else {
                    instructions += new ALU(target, a, target, ALUOP.OR)
                }
            case TokenType.NUMBER =>
                instructions += new ALU(target, consume(TokenType.NUMBER).text.toInt, target, ALUOP.OR)
            case TokenType.HEX_NUMBER =>
                instructions += new ALU(target, Integer.parseInt(consume(TokenType.HEX_NUMBER).text, 16), target, ALUOP.OR)
        }
    }

    private def parseAND(): Unit = {
        val target = Registers(consume(TokenType.REGISTER).text)


        get().tokenType match {
            case TokenType.REGISTER =>
                val a = Registers(consume(TokenType.REGISTER).text)
                if (get().tokenType == TokenType.REGISTER) {
                    instructions += new ALU(target,a, Registers(consume(TokenType.REGISTER).text),  ALUOP.AND)

                } else {
                    instructions += new ALU(target, a, target, ALUOP.AND)
                }
            case TokenType.NUMBER =>
                instructions += new ALU(target, consume(TokenType.NUMBER).text.toInt, target, ALUOP.AND)
            case TokenType.HEX_NUMBER =>
                instructions += new ALU(target,Integer.parseInt( consume(TokenType.HEX_NUMBER).text,16), target, ALUOP.AND)
        }
    }



    private def parseDEC(): Unit = {
        val reg = Registers(consume(TokenType.REGISTER).text)
        instructions += new ALU(reg, 1, reg, ALUOP.SUB)
    }
    private def parseINC(): Unit = {
        val reg = Registers(consume(TokenType.REGISTER).text)
        instructions += new ALU(reg, 1, reg, ALUOP.ADD)
    }

    private def parseSUB(): Unit = {
        val r1 = Registers(consume(TokenType.REGISTER).text)


        get().tokenType match {
            case TokenType.REGISTER =>
                val r2 = Registers(consume(TokenType.REGISTER).text)
                if (get().tokenType == TokenType.REGISTER) {
                    instructions += new ALU(r1,r2 , Registers(consume(TokenType.REGISTER).text), ALUOP.SUB)

                } else {
                    instructions += new ALU(r1, r2, r1, ALUOP.SUB)
                }
            case TokenType.NUMBER =>
                instructions += new ALU(r1, consume(TokenType.NUMBER).text.toInt, r1, ALUOP.SUB)
        }
    }

    private def parseLSR(): Unit = {
        //todo x<<y->z
        val target = Registers(consume(TokenType.REGISTER).text)

        get().tokenType match {
            case TokenType.REGISTER =>
                val a = Registers(consume(TokenType.REGISTER).text)
                if (get().tokenType == TokenType.REGISTER) {
                    instructions += new ALU(Registers(consume(TokenType.REGISTER).text), a, target, ALUOP.LSR)
                } else {
                    instructions += new ALU(target, a, target, ALUOP.LSR)
                }

            case TokenType.NUMBER =>
                instructions += new ALU(target, consume(TokenType.NUMBER).text.toInt, target, ALUOP.LSR)
        }


    }


    private def parseLSL(): Unit = {
//todo x<<y->z
        val target = Registers(consume(TokenType.REGISTER).text)

        get().tokenType match {
            case TokenType.REGISTER =>
                val a = Registers(consume(TokenType.REGISTER).text)
                if (get().tokenType == TokenType.REGISTER) {
                    instructions += new ALU( Registers(consume(TokenType.REGISTER).text),a, target, ALUOP.LSL)
                } else {
                    instructions += new ALU( target, a,target, ALUOP.LSL)
                }

            case TokenType.NUMBER =>
                instructions += new ALU(target, consume(TokenType.NUMBER).text.toInt, target, ALUOP.LSL)
        }


    }

    private def parseCSL(): Unit = {
        //todo x<<y->z
        val target = Registers(consume(TokenType.REGISTER).text)

        get().tokenType match {
            case TokenType.REGISTER =>
                val a = Registers(consume(TokenType.REGISTER).text)
                if (get().tokenType == TokenType.REGISTER) {
                    instructions += new ALU(Registers(consume(TokenType.REGISTER).text), a, target, ALUOP.CSL)
                } else {
                    instructions += new ALU(target, a, target, ALUOP.CSL)
                }

            case TokenType.NUMBER =>
                instructions += new ALU(target, consume(TokenType.NUMBER).text.toInt, target, ALUOP.CSL)
        }


    }
    private def parseADD(): Unit = {

        val r1 = Registers(consume(TokenType.REGISTER).text)

        get().tokenType match {
            case TokenType.REGISTER =>
                val r2 = Registers(consume(TokenType.REGISTER).text)
                if (get().tokenType == TokenType.REGISTER) {
                    instructions += new ALU(r1,r2,Registers(consume(TokenType.REGISTER).text), ALUOP.ADD)
                } else {
                    instructions += new ALU(r1, r2, r1, ALUOP.ADD)
                }

            case TokenType.NUMBER =>
                instructions += new ALU(r1, consume(TokenType.NUMBER).text.toInt, r1, ALUOP.ADD)
        }


    }

    private def parseRET(): Unit = {
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
                instructions += new ALU(r1, n1, Registers.$0, ALUOP.SUB)
            case TokenType.REGISTER =>
                instructions += new ALU(r1, Registers(consume(TokenType.REGISTER).text), Registers.$0, ALUOP.SUB)
            case TokenType.HEX_NUMBER =>
                val n1 = Integer.parseInt(consume(TokenType.HEX_NUMBER).text,16)
                instructions += new ALU(r1, n1, Registers.$0, ALUOP.SUB)

        }
    }

    private def parsePUSH(): Unit = {
        get().tokenType match
            case TokenType.REGISTER => instructions += new PUSH(Registers(consume(TokenType.REGISTER).text))
            case TokenType.NUMBER =>instructions += new PUSH( Registers.$0,consume(TokenType.NUMBER).text.toInt)
            case TokenType.HEX_NUMBER => instructions += new PUSH( Registers.$0,Integer.parseInt(consume(TokenType.HEX_NUMBER).text,16))
    }

    private def parsePOP(): Unit = {
        instructions += new POP(Registers(consume(TokenType.REGISTER).text))
    }

    private def parseCALL(): Unit = {
        instructions += new CALL(consume(TokenType.WORD))
    }

    private def parseJMP(token: Token): Unit = {
        if (get().tokenType == TokenType.DOT) {
            consume(TokenType.DOT)
            instructions += new JMP(token, lastLabel + "." + consume(TokenType.WORD).text)
        } else {
            instructions += new JMP(token, consume(TokenType.WORD).text)
        }
    }

    var lastLabel: String = ""

    private def parseLabel(token: Token): Unit = {
        if (token.tokenType == TokenType.DOT) {
            instructions += new Label(lastLabel + "." + consume(TokenType.WORD).text)
        } else {
            lastLabel = token.text
            instructions += new Label(token.text)
        }
        consume(TokenType.COLON)

    }


    def parseLW(): Unit = {
        get().tokenType match {
            case TokenType.LBRACKET =>
                consume(TokenType.LBRACKET)
                get().tokenType match{
                    case TokenType.SP =>{
                        val r1: Register = Registers(consume(TokenType.SP).text)
                        var offset: Int = get().tokenType match {
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
                        instructions += new LW(r1, offset, r2)
                    }

                    case TokenType.WORD =>{
                        var offset: Int = data.variable(consume(TokenType.WORD).text).number

                         get().tokenType match {
                                case TokenType.PLUS =>
                                    consume(TokenType.PLUS)
                                    offset *=  1
                                case TokenType.MINUS =>
                                    consume(TokenType.MINUS)
                                    offset *= -1
                            }
                        val r1: Register = Registers(consume(TokenType.REGISTER).text)
                        consume(TokenType.RBRACKET)
                        val r2: Register = Registers(consume(TokenType.REGISTER).text)
                        instructions += new LW(r1, offset, r2)


                    }
                }




            case TokenType.REGISTER =>
                val r1: Register = Registers(consume(TokenType.REGISTER).text)
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new LW(r1, r2)
            case TokenType.WORD =>
                val variable: Variable = data.variable(consume(TokenType.WORD).text)

                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new LWV(variable, r2)
        }


    }

    def parseSW(): Unit = {



        val r1: Register = Registers(consume(TokenType.REGISTER).text)



        get().tokenType match {
            case TokenType.LBRACKET =>{

                consume(TokenType.LBRACKET)
                get().tokenType match {

                    case TokenType.WORD => {
                        var offset: Int = data.variable(consume(TokenType.WORD).text).number

                        get().tokenType match {
                            case TokenType.PLUS =>
                                consume(TokenType.PLUS)
                                offset *= 1
                            case TokenType.MINUS =>
                                consume(TokenType.MINUS)
                                offset *= -1
                        }
                        val r2: Register = Registers(consume(TokenType.REGISTER).text)
                        consume(TokenType.RBRACKET)
                        instructions += new SW(r1, offset, r2)
                    }
                    case TokenType.SP =>{
                        val r2: Register = Registers(consume(TokenType.SP).text)
                        var offset: Int = get().tokenType match {
                            case TokenType.PLUS =>
                                consume(TokenType.PLUS)
                                1
                            case TokenType.MINUS =>
                                consume(TokenType.MINUS)
                                -1
                        }
                        offset *= consume(TokenType.NUMBER).text.toInt
                        consume(TokenType.RBRACKET)
                        instructions += new SW(r1, offset, r2)
                    }
                }
            }


            case TokenType.REGISTER =>
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new SW(r1, r2)
            case TokenType.WORD =>
                val variable: Variable = data.variable(consume(TokenType.WORD).text)
                instructions += new SWV(r1,variable)
        }


    }

    def parseMOV(): Unit = {
        get().tokenType match {
            case TokenType.HEX_NUMBER =>
                val n1 = Integer.parseInt(consume(TokenType.HEX_NUMBER).text,16)
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new ALU(Registers.$0, n1, r2, ALUOP.ADD)

            case TokenType.NUMBER =>
                val n1 = consume(TokenType.NUMBER).text.toInt
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new ALU(Registers.$0, n1, r2, ALUOP.ADD)
            case TokenType.REGISTER =>
                val r1 = Registers(consume(TokenType.REGISTER).text)
                val r2: Register = Registers(consume(TokenType.REGISTER).text)
                instructions += new ALU(Registers.$0, r1, r2, ALUOP.ADD)
        }


    }

    def get(index: Int = 0): Token = {
        tokens(pos + index)
    }

    def next(): Token = {
        val t = tokens(pos)
        pos += 1
        t
    }

    def consume(tokenType: TokenType): Token = {
        val current = get()
        if (tokenType != current.tokenType) {
            throw new RuntimeException("Line "+ line + " Token " + current + " doesn't match " + tokenType)
        }
        pos += 1
        current
    }

}
