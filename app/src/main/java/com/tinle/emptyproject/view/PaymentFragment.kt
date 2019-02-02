package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fantasticsoft.TransactionType
import com.fantasticsoft.gposlinklib.EdcType
import com.fantasticsoft.gposlinklib.PosLinkCallback
import com.fantasticsoft.gposlinklib.PostLinkHandler
import com.pax.poslink.PaymentResponse
import com.pax.poslink.ProcessTransResult
import com.tinle.emptyproject.MainActivity
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.core.AppExecutor
import kotlinx.android.synthetic.main.fragment_payment.*
import javax.inject.Inject
import android.widget.ArrayAdapter
import com.tinle.emptyproject.vm.PaymentVM

class PaymentFragment:BaseFragment() {
    private lateinit var posHandler:PostLinkHandler
    lateinit var viewModel: PaymentVM
    var refNumber:Int = 12

    @Inject
    lateinit var exexutor:AppExecutor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_payment, container, false)
        posHandler = (activity as MainActivity).getPosHandler()
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(PaymentVM::class.java)

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //setToolbarVisibility(View.GONE)
        submitPayment.setOnClickListener{
            if(posHandler != null) {
                progressDialog.setTitle("Process Payment")
                progressDialog.setMessage("Processing payment...")
                progressDialog.show()

                val edcType = getTenderTypee()
                val tranType = getTransType()

                posHandler.ProcessPayment(getSaleAmount(), EdcType.CREDIT, "$refNumber", TransactionType.SALE, object :PosLinkCallback{
                    override fun onProcessSuccess(p0: PaymentResponse?) {
                        hideProgDialog(true)
                        refNumber++
                    }

                    override fun onProcessFailed(p0: ProcessTransResult?) {
                        hideProgDialog(false)

                    }
                })
            }
        }

        val paymentAdapter = ArrayAdapter.createFromResource(activity,
                R.array.payment_tenders, R.layout.payment_type_spinner_text)

        paymentAdapter.setDropDownViewResource(R.layout.payment_type_spinner_text_dropdown)
        tenderType.adapter = paymentAdapter

        val transactionAdapter = ArrayAdapter.createFromResource(activity,
                R.array.payment_trans, R.layout.payment_type_spinner_text)

        transactionAdapter.setDropDownViewResource(R.layout.payment_type_spinner_text_dropdown)
        transType.adapter = transactionAdapter
        //SET DEFAULT TO CREDIT
        transType.setSelection(2)
    }

    private fun getSaleAmount():Int{
        var amountInCents:Int = 0;
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

    private fun getTransType():TransactionType{
        val transType =  TransactionType.values()[transType.selectedItemPosition]
        return transType
    }

    private fun getTenderTypee():EdcType{
        val edcType = EdcType.values()[tenderType.selectedItemPosition + 1]
        return edcType
    }


     fun hideProgDialog(success:Boolean) {
         exexutor.mainThread().execute({
            if(success) {
                Toast.makeText(activity, "Payment process successfully", Toast.LENGTH_LONG).show()
                saleAmount.setText("")
            }
            hideProgress()
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