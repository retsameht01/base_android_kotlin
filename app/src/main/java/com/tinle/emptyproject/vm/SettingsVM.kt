package com.tinle.emptyproject.vm

import android.arch.lifecycle.ViewModel
import com.tinle.emptyproject.core.PreferenceStore
import javax.inject.Inject

class SettingsVM @Inject constructor(
        private val prefStore: PreferenceStore

):ViewModel() {

    fun saveAPI(apiValue: String) {
        prefStore.saveAPI(apiValue)
    }

    fun getAPIValue():String{
        return prefStore.getAPI()
    }
}