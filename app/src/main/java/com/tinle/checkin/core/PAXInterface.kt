package com.tinle.checkin.core

interface PAXInterface {
    fun ProcessPAXCommand(command:String, requestJson:String, requestCode:Int )
}