package com.example.chatexample.helper

import android.content.Context
import android.content.SharedPreferences


object SettingsHelper {
    private const val PREFS_NAME = "settings"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var url: String?
        get() = prefs.getString("url", null)
        set(value) = prefs.edit().putString("url", value).apply()
}
