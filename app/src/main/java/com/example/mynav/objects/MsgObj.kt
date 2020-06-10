package com.example.mynav.objects

class MsgObj (
    var name:String ,
    var message:String,
    var num:String
){
    override fun toString(): String {
        return " This is $name  $message"
    }
}