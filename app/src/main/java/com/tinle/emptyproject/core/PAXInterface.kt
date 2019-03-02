package com.tinle.emptyproject.core

interface PAXInterface {
    fun ProcessPAXCommand(command:String, action:String, requestJson:String, requestCode:Int )
}