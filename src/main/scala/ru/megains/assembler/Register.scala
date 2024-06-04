package ru.megains.assembler

class Register(val id:Short) {
    
    private var _value:Short = 0

    def value:Short = _value
    def value_=(num:Short): Unit = {
        _value = num
    }
    override def toString: String = "$"+id +" = "+ value
}
