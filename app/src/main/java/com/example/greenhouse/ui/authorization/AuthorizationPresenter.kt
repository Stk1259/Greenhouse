package com.example.greenhouse.ui.authorization

import android.content.Context
import android.util.Log
import com.example.greenhouse.R
import com.example.greenhouse.data.preferences.TokenManager
import com.example.greenhouse.data.repository.Repository
import com.example.greenhouse.ui.base.BasePresenter
import kotlinx.coroutines.launch
import moxy.presenterScope

class AuthorizationPresenter(context: Context) : BasePresenter<AuthorizationView>() {
    private val repository = Repository(context)
    lateinit var phone: String

    fun sendPhoneNumber(phoneNumber: String?) = presenterScope.launch {
        Log.d("phoneNumber", phoneNumber.toString())
        if (phoneNumber.isNullOrEmpty()) {
            viewState.showMessage(R.string.error_message_empty)
        } else {
            phone = phoneNumber
            try {
                val response = repository.sendNumber(phone)
                Log.d("response is successful:", response.toString())
                viewState.gotConfirmationCode()
            } catch (e: Exception) {
                val errorMessage = getErrorValue(e)
                viewState.showMessage(errorMessage)
            }
        }
    }

    fun sendAuthCode(code: String?) = presenterScope.launch {
        if (code.isNullOrEmpty()) {
            viewState.showMessage(R.string.error_message_empty)
        } else {
            try {
                val response = repository.sendAuthCode(code, phone)
                viewState.onCheckComplete(response)
            } catch (e: Exception) {
                Log.e(e.cause.toString(), e.message.toString())
                val errorMessage = getErrorValue(e)
                viewState.showMessage(errorMessage)
            }
        }
    }
}