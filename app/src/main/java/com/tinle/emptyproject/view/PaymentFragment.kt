package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fantasticsoft.gposlinklib.PostLinkHandler
import com.pax.poslink.PaymentResponse
import com.tinle.emptyproject.MainActivity
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.core.AppExecutor
import kotlinx.android.synthetic.main.fragment_payment.*
import javax.inject.Inject
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.pax.poslink.PaymentRequest
import com.tinle.emptyproject.vm.PaymentVM

class PaymentFragment:BaseFragment() {
    private lateinit var posHandler:PostLinkHandler
    lateinit var viewModel: PaymentVM
    var refNumber:Int = 0
    private val PaymentRequestCode = 9

    @Inject
    lateinit var exexutor:AppExecutor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setToolbarVisibility(View.VISIBLE)
        var view = inflater.inflate(R.layout.fragment_payment, container, false)
        posHandler = (activity as MainActivity).getPosHandler()
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(PaymentVM::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //setToolbarVisibility(View.GONE)
        refNumber = viewModel.getTransactionCount()
        setPaymentClickHandler()
        val paymentAdapter = ArrayAdapter.createFromResource(activity,
                R.array.payment_types, R.layout.payment_type_spinner_text)

        paymentAdapter.setDropDownViewResource(R.layout.payment_type_spinner_text_dropdown)
        tenderType.adapter = paymentAdapter

        val transactionAdapter = ArrayAdapter.createFromResource(activity,
                R.array.payment_transactions, R.layout.payment_type_spinner_text)

        transactionAdapter.setDropDownViewResource(R.layout.payment_type_spinner_text_dropdown)
        transType.adapter = transactionAdapter
    }

    private fun setPaymentClickHandler(){
        submitPayment.setOnClickListener{
            val request = PaymentRequest()
            val saleAmt = getSaleAmount()
            val tip = getTipAmount()
            request.Amount = "$saleAmt"
            request.TipAmt = "$tip"
            request.TenderType = request.ParseTenderType(getTenderTypee())
            request.TransType = request.ParseTransType(getTransType())
            request.ECRRefNum = "1234"

            val json:String = Gson().toJson(request, PaymentRequest::class.java)
            val intent = Intent()
            intent.action ="com.gpos.paxrequest"
            intent.putExtra("RequestType", "PAYMENT")
            intent.putExtra("Data", json)
            startActivityForResult(intent, PaymentRequestCode)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            val resp = data.getStringExtra(Intent.EXTRA_TEXT)
            val paymentResponse = Gson().fromJson<PaymentResponse>(resp, PaymentResponse::class.java)
            viewModel.saveTransaction(paymentResponse)
            showToast("Payment complete")
        }
    }

    private fun getSaleAmount():Int{
        var amountInCents:Int = 0
        if(!saleAmount.text.toString().isEmpty()){
            val amount =  saleAmount.currencyDouble
            amountInCents = (amount * 100).toInt()
        }
        return amountInCents
    }

    private fun getTipAmount():Int{
        var amtCents = 0;
        if(!tipAmount.text.toString().isEmpty()) {
            val amt = tipAmount.currencyDouble
            amtCents = (amt*100).toInt()
        }
        return amtCents
    }

    private fun getTransType():String{
        val result = transType.selectedItem.toString()
        return result
    }

    private fun getTenderTypee():String{
        val edcType = tenderType.selectedItem.toString()
        return edcType
        //return edcType?:"CREDIT" COOL USE of elvis operator
    }


     fun hideProgDialog(success:Boolean) {
         exexutor.mainThread().execute({
            if(success) {
                Toast.makeText(activity, "Payment process successfully", Toast.LENGTH_LONG).show()
                saleAmount.setText("")
            }
            hideDialog()
         })
    }

    override fun onBusEvent(event: AppEvent) {

    }

    inner class SaleAmountTextListener: PhoneNumberFormattingTextWatcher() {
        override fun afterTextChanged(s: Editable) {
            super.afterTextChanged(s)
            val phone = s.toString()
        }
    }
}