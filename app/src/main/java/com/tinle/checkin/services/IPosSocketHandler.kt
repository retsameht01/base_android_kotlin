package com.tinle.checkin.services

import com.pax.poslink.*

interface IPosSocketHandler {
    fun sendManageCommand(manageRequest: ManageRequest, completionHandler: CompletionHandler<ManageResponse, ProcessTransResult>)
    fun sendBatchRequest(batchRequest: BatchRequest)
    fun sendPayRequest(paymentRequest: PaymentRequest)
}