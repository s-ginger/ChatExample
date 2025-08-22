package com.example.chatexample.helper

import android.content.Context
import android.content.SharedPreferences
import java.util.UUID


object SettingsHelper {
    private const val PREFS_NAME = "settings"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        val savedId = prefs.getString("idMy", null)
        if (savedId != null) {
            idMy = savedId
        } else {
            val newId = UUID.randomUUID().toString()
            idMy = newId
        }
    }

    var url: String?
        get() = prefs.getString("url", null)
        set(value) = prefs.edit().putString("url", value).apply()

    var idMy: String?
        get() = prefs.getString("idMy", null)
        set(value) = prefs.edit().putString("idMy", value).apply()
}
