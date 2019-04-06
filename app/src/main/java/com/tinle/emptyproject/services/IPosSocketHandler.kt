package com.tinle.emptyproject.services

import com.pax.poslink.*

interface IPosSocketHandler {
    fun sendManageCommand(manageRequest: ManageRequest, completionHandler: CompletionHandler<ManageResponse, ProcessTransResult>)
    fun sendBatchRequest(batchRequest: BatchRequest)
    fun sendPayRequest(paymentRequest: PaymentRequest)
}