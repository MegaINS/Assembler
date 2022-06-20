package ru.megains.assembler

import ru.megains.assembler.instruction.Instruction

import java.io.{BufferedReader, File, FileReader}
import java.util.stream.Collectors
import scala.collection.mutable.ArrayBuffer


object Main extends App {

    //
    //   val assCode = """lw $1 200
    //      |lw $2 201
    //      |add $3 $1 $2
    //      |sw $3 202
    //      |""".stripMargin
    //
    //


    val file: File = new File("Z:\\Assembler\\logisim\\CODE.txt")
    val fileRead:FileReader = new FileReader(file)
    val buff:BufferedReader = new BufferedReader(fileRead)

    val assCode = buff.lines().collect(Collectors.joining("\n"));

    Lexer.setCodeText(assCode)

    val tokens: ArrayBuffer[Token] = Lexer.tokenize



    var a = 0
    print(a+" ")
    for (token <- tokens) {

        token.tokenType match {

            case TokenType.EOL =>
                println()
                a+=1
                print(a+" ")

            case _ =>
                print(token.tokenType)
                print(" ")
        }

    }

    Parser.setTokens(tokens)
    val instructions: ArrayBuffer[Instruction] = Parser.parse()

    for (instruction <- instructions) {
        println(instruction)
//        token.tokenType match {
//
//            case TokenType.EOL =>
//                println()
//                a+=1
//                print(a+" ")
//
//            case _ =>
//                print(token.tokenType)
//                print(" ")
//        }

    }
    //
    //    println(assCode)
    //
    //    assCode.split("\r\n")
    //            .map(_.split(" "))
    //            .map(Parser.parse)
    //            .map(_.getByteCode)
    //            .map((a) => a._1.toHexString + " " + a._2.toHexString)
    //            .foreach(println)

}
