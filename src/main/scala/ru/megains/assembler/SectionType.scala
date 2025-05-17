package ru.megains.assembler

import ru.megains.assembler.TokenType.TokenType

enum SectionType(val tokenType:TokenType) {


    case CODE extends SectionType(TokenType.CODE)
    case SET  extends SectionType(TokenType.SET)
    case DATA  extends SectionType(TokenType.DATA)
    case NONE  extends SectionType(null)
}