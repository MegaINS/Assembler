package ru.megains.assembler

import TokenType.TokenType

class Token(val tokenType: TokenType,val text:String) {


    override def toString = s"Token($tokenType,$text)"
}
