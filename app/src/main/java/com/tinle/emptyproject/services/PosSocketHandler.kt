package com.tinle.emptyproject.services

import com.pax.poslink.*

class PosSocketHandler: IPosSocketHandler {
    override fun sendManageCommand(manageRequest: ManageRequest, completionHandler: CompletionHandler<ManageResponse, ProcessTransResult>) {

    }


    override fun sendBatchRequest(batchRequest: BatchRequest) {

    }

    override fun sendPayRequest(paymentRequest: PaymentRequest) {

    }

}