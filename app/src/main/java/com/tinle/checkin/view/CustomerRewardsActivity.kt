package com.tinle.checkin.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tinle.checkin.R
import com.tinle.checkin.data.RewardsMember
import com.tinle.checkin.data.Promotion
import com.tinle.checkin.vm.CustomerRewardsVM
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_customer_rewards.*
import javax.inject.Inject

class CustomerRewardsActivity:AppCompatActivity() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    lateinit var vieModel: CustomerRewardsVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_customer_rewards)
        vieModel = ViewModelProviders.of(this, vmFactory).get(CustomerRewardsVM::class.java)
        vieModel.getCustomerInfo().observe(this, Observer<RewardsMember>{
            welcomeText.text = "Welcome, ${it!!.FirstName}"
            rewardPoints.text = "${it!!.RewardPoints} pts"
        })
        val layoutMgr = LinearLayoutManager(this)
        rewardsList.layoutManager = layoutMgr
        vieModel.getPromotions().observe(this, Observer<List<Promotion>>{
            rewardsList.adapter = PromoAdapter(it!!)
        })
    }


    inner class PromoAdapter(val promos:List<Promotion>): RecyclerView.Adapter<PromoHolder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PromoHolder {
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
        }

    }

    inner class PromoHolder(view: View): RecyclerView.ViewHolder(view){
        val promoTxt: TextView = view.findViewById(R.id.promoTxt)
        val points: TextView = view.findViewById(R.id.pointsCost)
    }
}