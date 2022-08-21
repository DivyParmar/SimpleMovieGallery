package com.myapp.simplegallery.database

import android.content.Context
import android.content.SharedPreferences

class AppPreference private constructor() {

    companion object {
        private const val SHARED_PREF_NAME = "SharedPreferenceSimpleGallery"

        private var appPreference: AppPreference? = null
        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null
        fun getInstance(context: Context): AppPreference? {
            if (appPreference == null) {
                appPreference = AppPreference()
                sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
                editor = sharedPreferences!!.edit()
            }
            return appPreference
        }
    }

    fun writeString(key: String, text: String) {
        editor!!.putString(key, text)
        editor!!.commit()
    }

    fun writeBoolean(key: String, boolean: Boolean) {
        editor!!.putBoolean(key, boolean)
        editor!!.commit()
    }

    fun readBoolean(key: String, boolean : Boolean): Boolean {
        return sharedPreferences!!.getBoolean(key, boolean)
    }

    fun readString(key: String, defaultText: String): String {
        return sharedPreferences!!.getString(key, defaultText)!!
    }

    fun writeInt(key: String, value: Int) {
        editor!!.putInt(key, value)
        editor!!.commit()
    }

    fun contains(key : String): Boolean{
        return sharedPreferences!!.contains(key)
    }
    fun readInt(key: String, value: Int): Int {
        return sharedPreferences!!.getInt(key, value)
    }

}


