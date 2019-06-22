package com.tinle.checkin.core

import com.pax.poslink.PaymentResponse
import com.tinle.checkin.data.PaymentTransaction

object PaymentUtil{
     fun initPaymentTransaction( paymentResp:PaymentResponse): PaymentTransaction {
        val payTrans = PaymentTransaction()
        payTrans.AuthCode = paymentResp.AuthCode
        payTrans.ApprovedAmount = paymentResp.ApprovedAmount
        payTrans.AvsResponse = paymentResp.AvsResponse
        payTrans.BogusAccountNum = paymentResp.BogusAccountNum
        payTrans.CardType = paymentResp.CardType
        payTrans.CvResponse = paymentResp.CvResponse
        payTrans.HostCode = paymentResp.HostCode
        payTrans.HostResponse = paymentResp.HostResponse
        payTrans.Message = paymentResp.Message
        payTrans.RefNum = paymentResp.RefNum
        payTrans.RequestedAmount = paymentResp.RequestedAmount
        payTrans.ResultCode = paymentResp.ResultCode
        payTrans.ResultTxt = paymentResp.ResultTxt
        payTrans.Timestamp = paymentResp.Timestamp
        payTrans.SigFileName = paymentResp.SigFileName
        payTrans.SignData = paymentResp.SignData
        payTrans.ExtData = paymentResp.ExtData
        return payTrans
    }
}