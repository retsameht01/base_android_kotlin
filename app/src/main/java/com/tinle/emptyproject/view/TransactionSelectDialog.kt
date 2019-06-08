package com.tinle.emptyproject.view

import android.content.Context
import android.support.v4.app.DialogFragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.tinle.emptyproject.R
import android.widget.TextView
import com.tinle.emptyproject.data.PaymentTransaction

class  TransactionSelectDialog:DialogFragment() {
    lateinit var rv: RecyclerView
    lateinit var adapter: MyAdapter
    lateinit var transactions: List<PaymentTransaction>
    lateinit var callback: OnTransactionSelectListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            transactions = arguments!!.getSerializable("transList") as List<PaymentTransaction>
        }

        callback = targetFragment as OnTransactionSelectListener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.dialog_transaction_select, container)
        //RECYCER
        rv = rootView.findViewById(R.id.transactionList)
        rv.layoutManager = LinearLayoutManager(this.context)

        //ADAPTER
        adapter = MyAdapter(this.context!!, transactions)
        rv.adapter = adapter
        this.dialog.setTitle("Transactions")
        return rootView
    }

    inner class MyAdapter(var c: Context, var trans:List<PaymentTransaction>) : RecyclerView.Adapter<MyHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.transaction_item_view, parent, false)
            return MyHolder(v)
        }

        override fun onBindViewHolder(holder: MyHolder, position: Int) {
            holder.itemView.setOnClickListener {
                callback.onSelectTransaction(transactions[position])
                dialog.dismiss()
            }
            holder.nameTxt.setText(trans[position].RefNum)
            holder.payType.setText(trans[position].CardType)
            holder.payAmt.setText(trans[position].RequestedAmount)
            holder.timeStamp.setText(trans[position].Timestamp)
        }

        override fun getItemCount(): Int {
            return transactions.size
        }
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameTxt: TextView
        var payType: TextView
        var payAmt: TextView
        var timeStamp: TextView

        init {
            nameTxt = itemView.findViewById(R.id.nameTxt)
            payType = itemView.findViewById(R.id.paymentType)
            payAmt = itemView.findViewById(R.id.paymentAmt)
            timeStamp = itemView.findViewById(R.id.transTime)
        }
    }

    interface OnTransactionSelectListener{
        fun onSelectTransaction(trans:PaymentTransaction)
    }

}