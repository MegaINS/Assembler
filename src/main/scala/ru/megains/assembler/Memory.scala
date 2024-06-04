package ru.megains.assembler

object Memory {



    private val memory:Array[Short] = new Array[Short](65535)


    def save(address:Short,data:Short): Unit = {
        memory(address) = data
    }

    def load(address:Short):Short ={
        memory(address)
    }

    def printMemory(length:Int): Unit = {
        for(i <- 0 to length by 10){
            print(memory(i)+" ")
            print(memory(i+1)+" ")
            print(memory(i+2)+" ")
            print(memory(i+3)+" ")
            print(memory(i+4)+" ")
            print(memory(i+5)+" ")
            print(memory(i+6)+" ")
            print(memory(i+7)+" ")
            print(memory(i+8)+" ")
            print(memory(i+9))
            println()
        }
    }
}
