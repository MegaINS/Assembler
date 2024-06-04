package ru.megains.assembler

import ru.megains.assembler.TokenType.TokenType

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Lexer {

    val tokens = new ArrayBuffer[Token]()
    var codeTest:String = ""
    var length: Int = -1
    var pos: Int = 0


    val OPERATOR_CHARS = "+-:@_.[]"
    val OPERATOR_TOKENS: Array[TokenType.Value] = Array(TokenType.PLUS, TokenType.MINUS,TokenType.COLON,TokenType.AT, TokenType.UNDERSCORE, TokenType.DOT,TokenType.LBRACKET,TokenType.RBRACKET)

    def setCodeText(input:String): Unit ={
        codeTest = input
        length = codeTest.length
    }

    def addToken(token: Token): Unit ={
        tokens += token
    }

    def addToken(tokenType: TokenType, text:String = ""): Unit ={
        addToken(new Token(tokenType,text))
    }


    def tokenize: ArrayBuffer[Token] = {
        while (pos < length) {
            val current: Char = peek(0)
            if (Character.isDigit(current)) tokenizeNumber()
            else if (Character.isLetter(current)) tokenizeWord();
            else if (current == '$') {
                next
                tokenizeRegister()
            }
            else if (OPERATOR_CHARS.indexOf(current) != -1) tokenizeOperator()
            else if(current == '\n'){
                addToken(TokenType.EOL)
                next
            }
            else { // whitespaces
                next
            }
        }
        addToken(TokenType.EOF)

        tokens
    }

    private def tokenizeNumber(): Unit = {
        val buffer: mutable.StringBuilder = new mutable.StringBuilder
        var current: Char = peek(0)
        while ( {
            Character.isDigit(current)
        }) {
            buffer.append(current)
            current = next
        }
        addToken(TokenType.NUMBER, buffer.toString)
    }

    def tokenizeWord(): Unit = {
        val buffer = new mutable.StringBuilder
        var current = peek(0)
        while ((Character.isLetterOrDigit(current)  || (current == '_')) && (current != ':') ) {
            buffer.append(current)
            current = next
        }
        val test = buffer.toString
        val tokenType = TokenType.values.map( v => v.toString -> v).toMap.getOrElse(test,  TokenType.WORD)

        addToken(tokenType, test)

    }
    private def tokenizeRegister(): Unit = {
        val buffer: mutable.StringBuilder = new mutable.StringBuilder("$")
        var current: Char = peek(0)
        while (Character.isDigit(current)) {
            buffer.append(current)
            current = next
        }
        addToken(TokenType.REGISTER, buffer.toString)
    }

    //    private def tokenizeHexNumber(): Unit = {
    //        val buffer: mutable.StringBuilder = new mutable.StringBuilder
    //        var current: Char = peek(0)
    //        while ( {
    //            Character.isDigit(current) || isHexNumber(current)
    //        }) {
    //            buffer.append(current)
    //            current = next
    //        }
    //        addToken(TokenType.HEX_NUMBER, buffer.toString)
    //    }

    private def isHexNumber(current: Char): Boolean = "abcdef".indexOf(Character.toLowerCase(current)) != -1

    private def tokenizeOperator(): Unit = {
        val position: Int = OPERATOR_CHARS.indexOf(peek(0))
        addToken(OPERATOR_TOKENS(position),peek(0).toString)
        next
    }

    private def next: Char = {
        pos += 1
        peek(0)
    }

    private def peek(relativePosition: Int): Char = {
        val position: Int = pos + relativePosition
        if (position >= length)  '\u0000'
        else codeTest.charAt(position)
    }
}
