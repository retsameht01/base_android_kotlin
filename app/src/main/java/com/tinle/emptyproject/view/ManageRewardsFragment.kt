package com.tinle.emptyproject.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.AppEvent
import com.tinle.emptyproject.data.RewardsMember

class ManageRewardsFragment:BaseFragment() {
    private lateinit var membersList:RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_manage_rewards, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        membersList = view.findViewById<RecyclerView>(R.id.rewardMembers)
        membersList.layoutManager = LinearLayoutManager(activity)
        membersList.adapter = MembersAdapter(getData())
    }

    private fun getData():List<RewardsMember>{
        var list:MutableList<RewardsMember> = mutableListOf()
        for(i in 0..4) {
            list.add(RewardsMember("member $i", "" +((i + 1) * 10)))
        }
        return list
    }


    override fun onBusEvent(event: AppEvent) {

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
            holder.member_name.text = member.name
            holder.member_points.text = "${member.points} Points"
        }

    }

    inner class MemberViewHolder(view:View):RecyclerView.ViewHolder(view) {

        val member_photo:ImageView = view.findViewById(R.id.mem_photo)
        val member_name:TextView = view.findViewById(R.id.mem_name)
        val member_points:TextView = view.findViewById(R.id.mem_points)


    }

}