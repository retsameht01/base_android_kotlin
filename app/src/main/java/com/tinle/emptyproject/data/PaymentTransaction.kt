package com.tinle.emptyproject.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.pax.poslink.PaymentResponse

@Entity
class PaymentTransaction{
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
    var AuthCode = ""
    var ApprovedAmount = ""
    var AvsResponse = ""
    var BogusAccountNum = ""
    var CardType = ""
    var CvResponse = ""
    var HostCode = ""
    var HostResponse = ""
    var Message = ""
    var RefNum = ""
    var RequestedAmount = ""
    var ResultCode = ""
    var ResultTxt = ""
    var Timestamp = ""
    var SigFileName = ""
    var SignData = ""
    var ExtData = ""



    /*

    CreditSale (with tips)
CreditAdjustTip
CreditVoidSale
CreditReturn
CreditAuth
CreditPostAuth


DebitSale (with tips, cashback)
DebitVoidSale

Manage:
InitilizeTerminal
ResetTerminal
TotalSumary
CloseBatch
DisplayDialog
Display User Input in currency

     */
}
