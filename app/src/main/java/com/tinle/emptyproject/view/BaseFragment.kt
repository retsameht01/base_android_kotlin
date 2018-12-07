package com.tinle.emptyproject.view

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment:Fragment() {
    @Inject
    lateinit var vmFactory:ViewModelProvider.Factory

    override fun onAttach(context: Context){
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(false)
        super.onAttach(context)

    }
}