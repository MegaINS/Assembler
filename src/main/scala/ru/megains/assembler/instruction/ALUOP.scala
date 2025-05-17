package ru.megains.assembler.instruction

object ALUOP extends Enumeration {
    type ALUOP = Value

    val ADD,ADDC = Value("+")
    val SUB,SUBC = Value("-")
    val AND = Value("&")
    val OR = Value("|")
    val XOR = Value("^")
    val NOT = Value("!")
    
    val LSR = Value(">>")
  
    val LSL = Value("<<")
    val CSL = Value("<<<")
    val >>> = Value







}