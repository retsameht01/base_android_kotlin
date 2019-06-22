package com.tinle.checkin.view

import android.content.Context
import android.support.annotation.NonNull
import android.widget.ArrayAdapter
import com.tinle.checkin.data.TransactionModel
import android.widget.TextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinle.checkin.R


class ManageTransGridViewAdapter(@NonNull context: Context, res:Int, val trans:List<TransactionModel>): ArrayAdapter<TransactionModel>(context, res) {

    var resource: Int
    var list: List<TransactionModel>
    var vi: LayoutInflater

    init {
        this.resource = res
        this.list = trans
        this.vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var holder: ViewHolder
        var retView: View

        if(convertView == null){
            retView = vi.inflate(R.layout.trans_grid_item, null)
            holder = ViewHolder()

            holder.transLabel = retView.findViewById(R.id.transLabel) as TextView?
            retView.tag = holder

        } else {
            holder = convertView.tag as ViewHolder
            retView = convertView
        }

        return convertView
    }

    internal class ViewHolder {
        var transLabel: TextView? = null
    }

}