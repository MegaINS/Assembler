package ru.megains.assembler

import ru.megains.assembler.instruction.{CALL, Instruction, JMP, Label}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

object Linker {


    var instructions: ArrayBuffer[Instruction] = null

    val instructions1: ArrayBuffer[Instruction] = new ArrayBuffer[Instruction]()
    var needLABEL: mutable.HashSet[String] = new mutable.HashSet[String]()
    var setLABEL: mutable.HashSet[String] = new mutable.HashSet[String]()
    val label:mutable.HashMap[String,Label] = new mutable.HashMap[String,Label]()
    var instructionCount: Int = Parser.set.org
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

        
        Parser.data.variable.values.foreach(v=>{
            v.IP = instructions1.length
            v.OP_IP = instructionCount
            instructionCount += v.length
            instructions1 += v
          
            Memory.save(v.IP.toShort,v.number.toShort)
        })






        instructions1
    }



}
