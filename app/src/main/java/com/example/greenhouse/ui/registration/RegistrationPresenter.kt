package com.example.greenhouse.ui.registration

import android.content.Context
import android.util.Log
import com.example.greenhouse.R
import com.example.greenhouse.data.preferences.TokenManager
import com.example.greenhouse.data.repository.Repository
import com.example.greenhouse.ui.base.BasePresenter
import kotlinx.coroutines.launch
import moxy.presenterScope

class RegistrationPresenter(context: Context, val phone: String) : BasePresenter<RegistrationView>() {
    private val repository = Repository(context)
    fun register(name: String?, userName: String?) = presenterScope.launch {
        if (name.isNullOrEmpty() || userName.isNullOrEmpty()) {
            viewState.showMassage(R.string.error_message_empty)
        } else {
            try {
                val response = repository.register(phone, name, userName)
                Log.d("response", response.toString())
                viewState.registered(response.userId)
                repository.insertRegistration(response.userId, phone, name, userName)
            }catch (e: Exception)
            {Log.e(e.cause.toString(), e.message.toString())}
        }
    }
}