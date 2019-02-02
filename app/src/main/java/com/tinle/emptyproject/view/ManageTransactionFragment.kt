package com.tinle.emptyproject.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fantasticsoft.gposlinklib.ManageRequestCallback
import com.fantasticsoft.gposlinklib.PostLinkHandler
import com.fantasticsoft.gposlinklib.ReportCallback
import com.pax.poslink.ProcessTransResult
import com.pax.poslink.ReportResponse
import com.tinle.emptyproject.MainActivity
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppExecutor
import kotlinx.android.synthetic.main.fragment_manage_transaction.*
import javax.inject.Inject


class ManageTransactionFragment:BaseFragment() {
    private lateinit var posHandler: PostLinkHandler
    @Inject
    lateinit var executor:AppExecutor
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_manage_transaction, container, false)
        posHandler = (activity as MainActivity).getPosHandler()
        return view;
    }
    //todo add tip, adjust tip, finish void transaction
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

        }

        totalSummaryBtn.setOnClickListener {

            posHandler.ReportSummary(object: ReportCallback{
                override fun onReportSuccess(p0: ReportResponse?) {
                    setResultText(p0)

                }

                override fun onReportFailed(p0: ProcessTransResult?) {
                    setResultText(null)
                }

            })
        }

        voidBtn.setOnClickListener {

        }

        adjustTipBtn.setOnClickListener {  }



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