package ru.megains.assembler.instruction

trait Instruction {
    def value(): Int

    def execute(): Unit
}
