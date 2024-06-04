package ru.megains.assembler

import ru.megains.assembler.TokenType.{REGISTER, TokenType}
import ru.megains.assembler.instruction.{CALL, Instruction, JMP, Label}

import java.io.{BufferedReader, File, FileReader}
import java.util
import java.util.stream.Collectors
import scala.collection.mutable
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
    val fileRead: FileReader = new FileReader(file)
    val buff: BufferedReader = new BufferedReader(fileRead)

    val assCode = buff.lines().collect(Collectors.joining("\n"));

    Lexer.setCodeText(assCode)

    val tokens: ArrayBuffer[Token] = Lexer.tokenize


    println("---Lexer---")
    var a = 0
    var lastToken: TokenType = TokenType.EOL
    for (token <- tokens) {

        if (lastToken != token.tokenType) {
            if (lastToken == TokenType.EOL) {
                println()
                print(a + " ")
                a += 1
            }
        }


        token.tokenType match {

            case TokenType.EOL =>

            case TokenType.EOF =>

            case _ =>
                print(token.tokenType)
                print(" ")

        }
        lastToken = token.tokenType
    }


    println()
    println("---Parser---")
    Parser.setTokens(tokens)
    val instructions: ArrayBuffer[Instruction] = Parser.parse()
    a = 0
    for (instruction <- instructions) {


        println(a + " " + instruction)
        a += 1

    }


    println()
    println("---Linker---")
    val instructions1: ArrayBuffer[Instruction] = Linker.link(instructions)
    a = 0
    for (instruction <- instructions1) {


        println(a + " " + instruction)
        a += 1

    }
    println()
    println("---Executor---")

    Executor.execute(instructions1)


    Registers.registerName.values.foreach(println(_))

    Memory.printMemory(200)

    println()
    println("---OP CODE---")
    a = 0
    var b = 0
    instructions1.foreach(i => {
        print(a + " "+ b)
        i.OPCode().foreach(j => {
            print(" " + j.toHexString)
            b +=1
        })
        println()
        a+=1
    })
    println("ALL instructions "+ b)
    a = 0
    instructions1.foreach(i => {

        i.OPCode().foreach(j => {
            print(j.toHexString +" " )
            a+=1
            if (a % 8 == 0)  println()
        })
        //if (a % 7 == 0)  println()

    })

}
