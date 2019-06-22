package com.tinle.checkin.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tinle.checkin.R
import com.tinle.checkin.core.AppEvent
import com.tinle.checkin.data.RewardsMember
import com.tinle.checkin.data.Promotion
import com.tinle.checkin.vm.CustomerRewardsVM
import kotlinx.android.synthetic.main.activity_customer_rewards.*

class RewardsFragment:BaseFragment() {
    lateinit var viewModel: CustomerRewardsVM

    override fun onBusEvent(event: AppEvent) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.activity_customer_rewards, container, false)
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(CustomerRewardsVM::class.java)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setToolbarVisibility(View.GONE)
        viewModel = ViewModelProviders.of(this, vmFactory).get(CustomerRewardsVM::class.java)
        viewModel.getCustomerInfo().observe(this, Observer<RewardsMember>{
            welcomeText.text = "Welcome, ${it!!.FirstName}"
            rewardPoints.text = "${it!!.RewardPoints} pts"
        })
        val layoutMgr = LinearLayoutManager(activity)
        rewardsList.layoutManager = layoutMgr
        viewModel.getPromotions().observe(this, Observer<List<Promotion>>{
            rewardsList.adapter = PromoAdapter(it!!)
        })
        backBtn.setOnClickListener{
            changeFragment(CheckinFragment())
        }
    }

    inner class PromoAdapter(val promos:List<Promotion>): RecyclerView.Adapter<PromoHolder>(){
        override fun onCreateViewHolder(p0: ViewGroup, position: Int): PromoHolder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.promo_row, p0, false)
            val params = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT)
            //view.layoutParams = params
            return PromoHolder(view)
        }

        override fun getItemCount(): Int {
            return promos.size
        }

        override fun onBindViewHolder(holder: PromoHolder, pos: Int) {
            val promo = promos[pos]
            holder.promoTxt.text = promo.Details
            holder.points.text = "${promo.Points} pts"
            holder.row.setOnClickListener {
                //Toast.makeText(activity, "${promo.Details} Clicked!!", Toast.LENGTH_LONG).show()
                val msg = "Redeem ${promo.Details} for ${promo.Points} Points?"
                showConfirmDialog(msg, "Redeem Rewards", DialogInterface.OnClickListener{_, i ->
                    changeFragment(CheckinFragment())
                })
            }
        }
    }

    inner class PromoHolder(view: View): RecyclerView.ViewHolder(view){
        val row:View = view
        val promoTxt: TextView = view.findViewById(R.id.promoTxt)
        val points: TextView = view.findViewById(R.id.pointsCost)
    }
}