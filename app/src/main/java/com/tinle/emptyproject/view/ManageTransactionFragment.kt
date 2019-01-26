package com.tinle.emptyproject.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.fantasticsoft.gposlinklib.ManageRequestCallback
import com.fantasticsoft.gposlinklib.PostLinkHandler
import com.pax.poslink.ProcessTransResult
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

        }


    }

}