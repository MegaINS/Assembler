package ru.megains.assembler

object TokenType extends Enumeration {
    type TokenType = Value
    val
        
    NUMBER,
    HEX_NUMBER,
    WORD,
    REGISTER,

    SET,
    DATA,
    CODE,
    START,
    END,

    PLUS,
    MINUS,
    COLON,
    AT,
    UNDERSCORE,
    DOT,
    LBRACKET,
    RBRACKET,
    SLASH,

    ADD,
    SUB,
    INC,
    DEC,
    AND,
    OR,
    XOR,
    LSL,
    LSR,
    CSL,
    MOV,
    LW,
    SW,

    CALL,
    RET,

    CMP,

    JMP,
    JE,
    JNE,
    JB,
    JBE,
    JG,
    JGE,

    PUSH,
    POP,

    SP,
    IP,


    EOL,
    EOF,

    SHUTDOWN

    = Value
}
