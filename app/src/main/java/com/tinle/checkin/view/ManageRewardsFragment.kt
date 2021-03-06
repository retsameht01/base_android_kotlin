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
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.tinle.checkin.R
import com.tinle.checkin.core.AppEvent
import com.tinle.checkin.data.RewardsMember
import com.tinle.checkin.vm.RewardsManagerVM
import kotlinx.android.synthetic.main.fragment_manage_rewards.*

class ManageRewardsFragment:BaseFragment() {
    private lateinit var viewModel: RewardsManagerVM
    lateinit var dialog: CheckoutDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setToolbarVisibility(View.GONE)
        var view = inflater.inflate(R.layout.fragment_manage_rewards, container, false)
        viewModel = ViewModelProviders.of(activity!!, vmFactory).get(RewardsManagerVM::class.java)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rewardMembers.layoutManager = LinearLayoutManager(activity)
        showLoadingDialog()
        viewModel.getAllCheckIns().observe(this, Observer<List<RewardsMember>> {
            rewardMembers.adapter = MembersAdapter(it!!)
            hideDialog()
        })
        backIcon.setOnClickListener {
            changeFragment(CheckinFragment())
        }

    }

    override fun onBusEvent(event: AppEvent) {

    }

    private fun handleCheckout(member:RewardsMember) {
        activity?.let {
            dialog = CheckoutDialog.newInstance("Checkout ${member.FirstName}?", clickListner)
            dialog.show(it.supportFragmentManager, "checkout checkoutDialog")
            dialog.isCancelable = false
        }

    }

    val clickListner = DialogInterface.OnClickListener { _, i ->
    if(dialog != null) {
        //TODO Handle the check out, need api to save the checkout.
        Toast.makeText(this.context, "Customer is checkedout", Toast.LENGTH_LONG).show()
    }
}

    inner class MembersAdapter(val members:List<RewardsMember>):RecyclerView.Adapter<MemberViewHolder>(){
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MemberViewHolder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.member_card, p0,false)
            return MemberViewHolder(view)
        }

        override fun getItemCount(): Int {
            return members.size
        }

        override fun onBindViewHolder(holder: MemberViewHolder, pos: Int) {
            val member = members[pos]
            holder.member_name.text = "${member.FirstName} ${member.LastName}"
            holder.member_points.text = "${member.RewardPoints} Points"
            holder.member_last_visit.text = viewModel.getLastVisitInfo(member.LastPurchase)
            holder.member_checkin_time.text = "Checkin 10:22 am"
            holder.member_checkout.setOnClickListener {
                handleCheckout(member)
            }
        }
    }

    inner class MemberViewHolder(view:View):RecyclerView.ViewHolder(view) {
        val member_photo:ImageView = view.findViewById(R.id.mem_photo)
        val member_name:TextView = view.findViewById(R.id.mem_name)
        val member_points:TextView = view.findViewById(R.id.mem_points)
        val member_checkout:TextView = view.findViewById(R.id.checkoutBtn)
        val member_last_visit:TextView = view.findViewById(R.id.lastVisit)
        val member_checkin_time:TextView = view.findViewById(R.id.checkinTime)
    }

}