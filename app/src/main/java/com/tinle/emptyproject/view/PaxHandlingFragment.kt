package com.tinle.emptyproject.view

import android.content.Intent
import com.tinle.emptyproject.core.PAXInterface

abstract class PaxHandlingFragment:BaseFragment(), PAXInterface {

     private val BATCH_ACTION = "BATCH"
     private val MANAGE_ACTION = "MANAGE"
     private val PAYMENT_ACTION = "PAYMENT"
     private val REPORT_ACTION = "REPORT"
     private val COMM_ACTION = "COMM"
     private val PAX_UTIL_ACTION = "com.gpos.paxrequest"
     private val REQUEST_TYPE_KEY = "RequestType"
     private val REQUEST_DATA_KEY = "Data"


    override fun ProcessPAXCommand(command:String, requestJson:String, requestCode:Int ){
         if(isValidCommand(command)) {
             val intent = Intent()
             intent.action = PAX_UTIL_ACTION
             intent.putExtra(REQUEST_TYPE_KEY, command)
             intent.putExtra(REQUEST_DATA_KEY, requestJson)
             startActivityForResult(intent, requestCode)
         }
         else {
            showToast("Invalid command: $command")
         }
    }

    private fun isValidCommand(command:String):Boolean {
        return command.equals(BATCH_ACTION) || command.equals(MANAGE_ACTION) || command.equals(PAYMENT_ACTION)
                || command.equals(REPORT_ACTION) || command.equals(COMM_ACTION)
    }
}