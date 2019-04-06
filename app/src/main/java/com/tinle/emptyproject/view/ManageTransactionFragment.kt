package com.tinle.emptyproject.view


import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fantasticsoft.gposlinklib.*
import com.pax.poslink.*
import com.tinle.emptyproject.MainActivity
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.data.PaymentTransaction
import com.tinle.emptyproject.vm.ManageTransactionVM
import kotlinx.android.synthetic.main.fragment_manage_transaction.*
import java.io.Serializable
import javax.inject.Inject


class ManageTransactionFragment:PaxHandlingFragment(), TransactionSelectDialog.OnTransactionSelectListener {

    private lateinit var posHandler: PostLinkHandler
    private val totalReportName = "LOCALTOTALREPORT"
    private val AdjustTip = "ADJUSTTIP"
    private val Void = "VOID"
    private lateinit var dialog: InputDialog
    private var selectedTransaction: PaymentTransaction? = null
    private val PAX_UTIL_ACTION = "com.gpos.paxrequest"
    private val INIT_REQUEST_CODE = 100
    private val RESET_REQUEST_CODE = 101
    private val PRINT_REQUEST_CODE = 102

    @Inject
    lateinit var executor:AppExecutor
    private lateinit var viewModel:ManageTransactionVM
    var lastAction  = "DEFAULT"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_manage_transaction, container, false)
        posHandler = (activity as MainActivity).getPosHandler()
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(ManageTransactionVM::class.java)
        return view;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null) {
            val result = data.getStringExtra(Intent.EXTRA_TEXT)
            if(requestCode == INIT_REQUEST_CODE) {
                showToast("Init success")
            }
        }
    }

    //todo add tip, adjust tip, finish void transactions
    //todo handle saving checkin info locally save user check in local, pull customer data and store locally in case the internet is down

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbarVisibility(View.GONE)
        closeBtn.setOnClickListener {
            changeFragment(PaymentFragment())
        }


        initBtn.setOnClickListener {
            ProcessPAXCommand("MANAGE", viewModel.getInitData(), INIT_REQUEST_CODE)
        }

        resetBtn.setOnClickListener {
            ProcessPAXCommand("MANAGE", viewModel.getResetData(), RESET_REQUEST_CODE)
            /*
            posHandler.ManageCommand("RESET", object: ManageRequestCallback{
                override fun onFailed(p0: ProcessTransResult?) {
                    executor.mainThread().execute {
                        Toast.makeText(activity, "Reset failed" , Toast.LENGTH_LONG).show()
                    }
                }

                override fun onManageCallback(p0: ManageResponse?) {
                    executor.mainThread().execute {
                        Toast.makeText(activity, "Reset Success" , Toast.LENGTH_LONG).show()
                    }
                }
            })

            */
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

        printBtn.setOnClickListener {
            ProcessPAXCommand("MANAGE", viewModel.getPrintData(), PRINT_REQUEST_CODE)
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
            lastAction = Void
            showTransDialog()
        }

        adjustTipBtn.setOnClickListener {
            lastAction = AdjustTip
            showTransDialog()
        }
    }

    private fun showTransDialog(){
        val dialog = TransactionSelectDialog()
        var bundle =  Bundle()
        bundle.putSerializable("transList", viewModel.getTransActions() as Serializable)
        dialog.arguments= bundle
        dialog.setTargetFragment(this, 1)
        dialog.show(fragmentManager, "trans_dialog")

    }

    override fun onSelectTransaction(trans: PaymentTransaction) {
        selectedTransaction = trans
        Toast.makeText(context, " Success ${trans.Timestamp}", Toast.LENGTH_LONG).show()
        if (lastAction.equals(Void, true)) {
            handleVoid(trans)
        }
        else if(lastAction.equals(AdjustTip, true)){
            handleAdjustTip(trans)
        }
    }

    private fun handleVoid(tras:PaymentTransaction) {
        progressDialog.setTitle("Process Transaction")
        progressDialog.setMessage("Processing void transactions...")
        progressDialog.show()

        posHandler.VoidTransaction(tras.RefNum,"", object:PosLinkCallback{
            override fun onProcessSuccess(p0: PaymentResponse?) {
                hideProgDialog(true, p0?.ResultTxt?:"unknown result")
            }

            override fun onProcessFailed(p0: ProcessTransResult?) {
                hideProgDialog(false, p0?.Msg?:"Failed")
            }
        })
    }

    private fun handleAdjustTip(trans: PaymentTransaction) {
        showTipDialog()
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
            hideDialog()
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

    private fun showTipDialog() {
        val fm: FragmentManager = fragmentManager!!
        dialog  = InputDialog.newInstance("Tip Amount", inputDialogClickListener)
        dialog.show(fm, "insert_tip")

    }

    val inputDialogClickListener = DialogInterface.OnClickListener { _, i ->
        if(dialog != null) {
            var amtCents = 0
            var tipAmount =  dialog.getInputText()
            try {
                amtCents = (tipAmount*100).toInt()
            }
            catch (e:Exception) {

            }
            progressDialog.setTitle("Process Transaction")
            progressDialog.setMessage("Processing adjust tip...")
            progressDialog.show()
            posHandler.AdjustTip("1", "${selectedTransaction?.RefNum}",  amtCents, object :PosLinkCallback{

                override fun onProcessSuccess(p0: PaymentResponse?) {
                    hideProgDialog(true, "success")
                }

                override fun onProcessFailed(p0: ProcessTransResult?) {
                    hideProgDialog(false, "failed")
                }
            })


        }
    }

}