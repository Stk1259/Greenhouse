package com.example.greenhouse.data.preferences

import android.content.Context
import android.content.SharedPreferences
import com.example.greenhouse.data.remote.request.RefreshToken
import com.example.greenhouse.data.remote.response.AccessToken
import com.example.greenhouse.data.repository.Repository

class TokenManagerImpl(context: Context): TokenManager {
    companion object{
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("TOKENS", Context.MODE_PRIVATE)

    override fun saveAccessToken(token: String) {
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply()
    }

    override fun saveRefreshToken(token: String) {
        sharedPreferences.edit().putString(REFRESH_TOKEN, token).apply()

    }

    override fun getAccessToken(): String? {
        return sharedPreferences.getString(ACCESS_TOKEN, null)
    }

    override fun getRefreshToken(): String? {
        return sharedPreferences.getString(REFRESH_TOKEN, null)
    }

    override fun clearTokens() {
        sharedPreferences.edit().remove(ACCESS_TOKEN).remove(REFRESH_TOKEN).apply()
    }
}