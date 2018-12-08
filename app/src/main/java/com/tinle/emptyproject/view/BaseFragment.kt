package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.support.v4.app.Fragment
import com.tinle.emptyproject.R
import com.tinle.emptyproject.core.BusListener
import com.tinle.emptyproject.core.EventBus
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment:Fragment(), BusListener {
    @Inject
    lateinit var vmFactory:ViewModelProvider.Factory

    override fun onAttach(context: Context){
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(false)
        super.onAttach(context)
        EventBus.addListener(this)
    }

    fun changeFragment(target:BaseFragment) {
        val trans =  activity!!.supportFragmentManager.beginTransaction()
        trans.replace(R.id.fragment_container,target)
        trans.commit();
    }

}