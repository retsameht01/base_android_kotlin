package com.tinle.emptyproject.core

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceStore @Inject constructor(
        val context: Context
)

{
    private val PREF = "MyPref"
    private val API_KEY = "API_KEY"
    private var sharePref: SharedPreferences
    //sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    init {
        sharePref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
    }

    fun saveAPI(api:String) {
        val editor = sharePref.edit()
        editor.putString(API_KEY, api)
        editor.commit()
    }

    fun getAPI():String {
        return sharePref.getString(API_KEY, "20002")

    }

}