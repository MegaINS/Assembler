package ru.megains.assembler.instruction

object ALUOP extends Enumeration {
    type ALUOP = Value
    val
        
    OR,
    XOR,
    NOT,
    >>,
    <<,
    >>>


    = Value

    val ADD,ADDC = Value("+")
    val SUB,SUBC = Value("-")
    val AND = Value("&")
}