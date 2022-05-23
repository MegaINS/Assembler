package ru.megains.assembler

object Main extends App {


   val assCode = """lw $1 200
      |lw $2 201
      |add $3 $1 $2
      |sw $3 202
      |""".stripMargin

    println(assCode)

    assCode.split("\r\n")
            .map(_.split(" "))
            .map(Parser.parse)
            .map(_.getByteCode)
            .map((a) => a._1.toHexString + " " + a._2.toHexString)
            .foreach(println)


}
