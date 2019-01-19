package com.tinle.emptyproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fantasticsoft.TransactionType
import com.fantasticsoft.gposlinklib.EdcType
import com.fantasticsoft.gposlinklib.PosLinkCallback
import com.fantasticsoft.gposlinklib.PoslinkActivity
import com.fantasticsoft.gposlinklib.PostLinkHandler
import com.pax.poslink.PaymentResponse
import com.pax.poslink.ProcessTransResult
import com.tinle.emptyproject.MainActivity
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.core.AppExecutor
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.todo_row.*
import javax.inject.Inject

class PaymentFragment:BaseFragment() {
    private lateinit var posHandler:PostLinkHandler

    @Inject
    lateinit var exexutor:AppExecutor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_payment, container, false)
        posHandler = (activity as MainActivity).getPosHandler()

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarVisibility(View.GONE)
        submitPayment.setOnClickListener{
            if(posHandler != null) {
                progressDialog.setTitle("Process Payment")
                progressDialog.setMessage("Processing payment...")
                progressDialog.show()
                posHandler.ProcessPayment(Integer.valueOf(saleAmount.text.toString().trim()), EdcType.CREDIT, "12", TransactionType.SALE, object :PosLinkCallback{
                    override fun onProcessSuccess(p0: PaymentResponse?) {
                        hideProgDialog(true)
                    }

                    override fun onProcessFailed(p0: ProcessTransResult?) {
                        hideProgDialog(false)
                    }

                })

            }
        }
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
}