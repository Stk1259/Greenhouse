package com.example.greenhouse.data.preferences

import com.example.greenhouse.data.remote.request.RefreshToken
import com.example.greenhouse.data.remote.response.AccessToken

interface TokenManager{
    fun saveAccessToken(token: String)
    fun getAccessToken(): String?
    fun saveRefreshToken(token: String)
    fun getRefreshToken(): String?
    fun clearTokens()
}