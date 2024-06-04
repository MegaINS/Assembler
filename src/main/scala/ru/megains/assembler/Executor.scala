package ru.megains.assembler

import ru.megains.assembler.instruction.Instruction

import scala.collection.mutable.ArrayBuffer

object Executor {



    var run = true

    def execute(inst: ArrayBuffer[Instruction]): Unit = {





        while (run){
            println( Registers.IP.value)
            val ip:Int = Registers.IP.value
            Registers.IP.value = (ip+1).toShort

            inst(ip).execute()

        }

    }

}
