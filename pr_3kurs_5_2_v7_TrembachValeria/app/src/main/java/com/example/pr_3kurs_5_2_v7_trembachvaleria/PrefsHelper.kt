package com.example.pr_3kurs_5_2_v7_trembachvaleria

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefsHelper(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveUser(login: String, password: String) {
        val userMap = mapOf("login" to login, "password" to password)
        val json = gson.toJson(userMap)
        prefs.edit().putString("user_data", json).apply()
    }

    fun getLogin(): String {
        val json = prefs.getString("user_data", "") ?: ""
        if (json.isEmpty()) return ""
        val type = object : TypeToken<Map<String, String>>() {}.type
        val map: Map<String, String> = gson.fromJson(json, type)
        return map["login"] ?: ""
    }

    fun getPassword(): String {
        val json = prefs.getString("user_data", "") ?: ""
        if (json.isEmpty()) return ""
        val type = object : TypeToken<Map<String, String>>() {}.type
        val map: Map<String, String> = gson.fromJson(json, type)
        return map["password"] ?: ""
    }

    fun isLoggedIn(): Boolean = getLogin().isNotEmpty()
}