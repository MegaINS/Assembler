package ru.megains.assembler

import ru.megains.assembler.Main.instructions
import ru.megains.assembler.instruction.{CALL, Instruction, JMP, Label, RET}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Linker {


    var instructions: ArrayBuffer[Instruction] = _

    val instructions1: ArrayBuffer[Instruction] = new ArrayBuffer[Instruction]()
    var needLABEL: mutable.HashSet[String] = new mutable.HashSet[String]()
    var setLABEL: mutable.HashSet[String] = new mutable.HashSet[String]()
    val label:mutable.HashMap[String,Label] = new mutable.HashMap[String,Label]()
    var instructionCount = 0
    def link(inst: ArrayBuffer[Instruction]): ArrayBuffer[Instruction] = {
        instructions = inst




        for(i<- inst.indices){
            inst(i) match {

                case l: Label =>
                    label += l.name -> l
                    l.IP = instructions1.length
                    l.OP_IP = instructionCount
                case ins:Any =>
                    instructionCount += ins.length
                    instructions1 += ins
            }
        }

        instructions1.foreach {
            case c: CALL =>
                c.IP = label(c.name.text).IP
                c.OP_IP = label(c.name.text).OP_IP
            case j: JMP =>
                j.IP = label(j.label).IP
                j.OP_IP = label(j.label).OP_IP
            case _ =>
        }


        instructions1
    }



}
