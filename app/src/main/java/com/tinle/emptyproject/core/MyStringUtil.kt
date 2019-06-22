package com.tinle.emptyproject.core

fun capFirstLetter(input:String):String {
    val words = input.split(" ").toMutableList()
    var output = ""
    for (word in words){
        output += word.capitalize() +" "
    }
    output = output.trim()
    return output
}