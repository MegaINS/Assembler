package ru.megains.assembler.instruction

trait Instruction {

    val OP:Int
    var length: Int = 1



    def value(): Int

    def OPCode():Array[Int]

    def execute(): Unit


}
