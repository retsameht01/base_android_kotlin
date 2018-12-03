package com.tinle.emptyproject.view

import android.os.Bundle
import android.support.v4.app.Fragment
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment:Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AndroidSupportInjection.inject(this)
        setHasOptionsMenu(false)
    }


}