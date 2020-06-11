package com.example.SMSManager.objects

class Contact(val name:String,val phno:String) {
    override fun toString(): String {
        return "$name"+"$phno"
    }
}