package com.example.greenhouse.data.preferences

import android.content.Context
import android.util.Log
import com.example.greenhouse.data.api.apiService
import com.example.greenhouse.data.remote.request.RefreshToken
import com.example.greenhouse.data.repository.Repository
import kotlinx.coroutines.*
import java.util.*

class TokenUpdater(private val tokenManager: TokenManagerImpl) {
    private val timer = Timer()
    init {
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run(): Unit = run { updateAccessToken() }
        }, 10 * 60 * 1000, 10 * 60 * 1000)
    }

    private fun updateAccessToken() = CoroutineScope(Dispatchers.Default).launch{
        Log.d("update", "started")
        val refreshToken = tokenManager.getRefreshToken()
        coroutineScope {  }
        val newAccessToken = apiService.refreshToken(RefreshToken(refreshToken.toString()))
        tokenManager.saveAccessToken(newAccessToken.accessToken)
    }

    fun stop() {
        timer.cancel()
    }
}
