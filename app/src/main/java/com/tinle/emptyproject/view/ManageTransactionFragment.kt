package com.tinle.emptyproject.view


import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fantasticsoft.gposlinklib.*
import com.pax.poslink.BatchResponse
import com.pax.poslink.PaymentResponse
import com.pax.poslink.ProcessTransResult
import com.pax.poslink.ReportResponse
import com.tinle.emptyproject.MainActivity
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.PaymentTransaction
import com.tinle.emptyproject.vm.ManageTransactionVM
import kotlinx.android.synthetic.main.fragment_manage_transaction.*
import java.io.Serializable
import javax.inject.Inject


class ManageTransactionFragment:BaseFragment(), TransactionSelectDialog.OnTransactionSelectListener {
    override fun onSelectTransaction(trans: PaymentTransaction) {
        Toast.makeText(context, " Success ${trans.Timestamp}", Toast.LENGTH_LONG).show()
    }

    private lateinit var posHandler: PostLinkHandler
    private val totalReportName = "LOCALTOTALREPORT"
    @Inject
    lateinit var executor:AppExecutor
    private lateinit var viewModel:ManageTransactionVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_manage_transaction, container, false)
        posHandler = (activity as MainActivity).getPosHandler()
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(ManageTransactionVM::class.java)
        return view;
    }
    //todo add tip, adjust tip, finish void transactions
    //todo handle saving checkin info locally save user check in locall, pull customer data and store locally in case the internet is down

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarVisibility(View.GONE)
        closeBtn.setOnClickListener {
            changeFragment(PaymentFragment())
        }

        initBtn.setOnClickListener {
            posHandler.ManageCommand("INIT", object: ManageRequestCallback{
                override fun onManageCallback(p0: ProcessTransResult?) {
                    executor.mainThread().execute {
                        Toast.makeText(activity, "Init " + p0?.Msg,Toast.LENGTH_LONG).show()
                    }
                }
            })

        }

        resetBtn.setOnClickListener {
            posHandler.ManageCommand("RESET", object: ManageRequestCallback{
                override fun onManageCallback(p0: ProcessTransResult?) {
                    executor.mainThread().execute {
                        Toast.makeText(activity, "Init " + p0?.Msg,Toast.LENGTH_LONG).show()
                    }
                }
            })
        }

        closeBatchBtn.setOnClickListener {

            progressDialog.setTitle("Process Batch")
            progressDialog.setMessage("Processing batch close...")
            progressDialog.show()
            posHandler.CloseBatch("BATCHCLOSE", "ALL" ,object:BatchCallback{
                override fun onSuccess(p0: BatchResponse?) {
                    hideProgDialog(true, "success")
                }

                override fun onFailed(p0: ProcessTransResult?) {
                    hideProgDialog(false, "failed")
                }
            })

        }

        totalSummaryBtn.setOnClickListener {

            progressDialog.setTitle("Process Report")
            progressDialog.setMessage("Processing summary report...")
            progressDialog.show()
            posHandler.ReportSummary( totalReportName, object: ReportCallback{
                override fun onReportSuccess(p0: ReportResponse?) {
                    setResultText(p0)
                    hideProgDialog(true, "success")

                }

                override fun onReportFailed(p0: ProcessTransResult?) {
                    setResultText(null)
                    hideProgDialog(false, "failed")
                }

            })
        }

        voidBtn.setOnClickListener {
            val dialog = TransactionSelectDialog()
            var bundle =  Bundle()
            bundle.putSerializable("transList", getSampleData() as Serializable)
            dialog.arguments= bundle
            dialog.setTargetFragment(this, 1)
            dialog.show(fragmentManager, "trans_dialog")
            /*

            progressDialog.setTitle("Process Transaction")
            progressDialog.setMessage("Processing void transactions...")
            progressDialog.show()

            posHandler.VoidTransaction("","", object:PosLinkCallback{
                override fun onProcessSuccess(p0: PaymentResponse?) {
                    hideProgDialog(true, p0?.ResultTxt?:"unknown result")
                }

                override fun onProcessFailed(p0: ProcessTransResult?) {
                   hideProgDialog(false, p0?.Msg?:"Failed")
                }
            })
            */
        }

        adjustTipBtn.setOnClickListener {
            progressDialog.setTitle("Process Transaction")
            progressDialog.setMessage("Processing adjust tip...")
            progressDialog.show()
            posHandler.AdjustTip("ecref", "ref",  100, object :PosLinkCallback{

                override fun onProcessSuccess(p0: PaymentResponse?) {
                    hideProgDialog(false, "success")
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onProcessFailed(p0: ProcessTransResult?) {
                    hideProgDialog(false, "failed")
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

            })
        }
    }

    private fun getSampleData():List<PaymentTransaction> {
        var trans = mutableListOf<PaymentTransaction>()
        for (i in 0..5) {
            val tran = PaymentTransaction()
            tran.Timestamp = "time $i"
            tran.CardType = "CREDIT$i"
            tran.RequestedAmount = "$23.23"
            tran.RefNum = "3"
            trans.add(tran)
        }
        trans.add(PaymentTransaction())
        return trans
    }

    fun hideProgDialog(success:Boolean, msg:String) {
        executor.mainThread().execute{
            if(success) {
                Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(activity, "failed", Toast.LENGTH_LONG).show()
            }
            hideProgress()
        }
    }

    private fun setResultText(reportResponse:ReportResponse?){
        if(reportResponse != null) {
            var resultString = "Credit amount ${reportResponse.CreditAmount} Credit Count: ${reportResponse.CreditCount} " +
                    "Cash Amount ${reportResponse.CashAmount} Cash Count: ${reportResponse.CashCount}" +
                    "Result code: ${reportResponse.ResultCode} Result Text: ${reportResponse.ResultTxt} TimeStamp: ${reportResponse.Timestamp}" +
                    "Trans Total ${reportResponse.TransTotal}"
            executor.mainThread().execute {
                resultText.setText(resultString)
            }
        }
        else {
            executor.mainThread().execute {
                resultText.setText("Error getting result report")
            }
        }
    }

}