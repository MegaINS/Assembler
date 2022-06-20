package ru.megains.assembler

abstract class Command {

    val opCode:Short

    def execute():Unit

    def getByteCode:(Short,Short)
    
}
