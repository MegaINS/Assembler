package ru.megains.assembler

object TokenType extends Enumeration {
    type TokenType = Value
    val
    NUMBER,
    WORD,
    REGISTER,

    PLUS,
    MINUS,
    COLON,
    AT,
    UNDERSCORE,
    DOT,
    LBRACKET,
    RBRACKET,

    ADD,
    SUB,
    INC,
    AND,

    MOV,
    LW,
    SW,

    CALL,
    RET,

    CMP,

    JMP,
    JE,
    JNE,

    PUSH,
    POP,

    SP,
    IP,


    EOL,
    EOF,

    SHUTDOWN

    = Value
}
