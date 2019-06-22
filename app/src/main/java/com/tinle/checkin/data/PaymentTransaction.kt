package com.tinle.checkin.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

@Entity
class PaymentTransaction:Serializable{
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
}
