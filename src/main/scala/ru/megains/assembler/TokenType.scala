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

    EOL,
    EOF

    = Value
}
