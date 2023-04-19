package com.example.greenhouse.ui.authorization

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import com.example.greenhouse.R
import com.example.greenhouse.data.remote.response.CodeResponse
import com.example.greenhouse.ui.base.BaseActivity
import com.example.greenhouse.ui.chats.ChatActivity
import com.example.greenhouse.ui.registration.RegistrationActivity
import com.hbb20.CountryCodePicker
import com.jakode.verifycodeedittext.VerifyCodeEditText
import moxy.ktx.moxyPresenter

class AuthorizationActivity : BaseActivity(), AuthorizationView {

    private val authorizationPresenter by moxyPresenter {
        AuthorizationPresenter(this)
    }
    lateinit var editTextCode: VerifyCodeEditText
    lateinit var button: Button
    lateinit var phone: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authorization)
        val countryCodePicker = findViewById<CountryCodePicker>(R.id.ccp)
        countryCodePicker.setDefaultCountryUsingNameCode("RU")
        val editText = findViewById<EditText>(R.id.editText_search)
        button = findViewById(R.id.button)
        editTextCode = findViewById(R.id.editText_code)
        countryCodePicker.registerCarrierNumberEditText(editText)
        button.setOnClickListener {
            phone = countryCodePicker.fullNumberWithPlus
            authorizationPresenter.sendPhoneNumber(phone)
        }

    }

    override fun gotConfirmationCode() {
        editTextCode.isVisible = true
        editTextCode.requestFocus()
        button.isVisible = false
        editTextCode.setCompleteListener { if (it) authorizationPresenter.sendAuthCode(editTextCode.text) }
    }

    override fun showMessage(message: Int) {
        Toast.makeText(
            this, message, Toast.LENGTH_LONG
        ).show()
    }

    override fun onCheckComplete(codeResponse: CodeResponse) {
        Log.d("code response", codeResponse.toString())
        if (codeResponse.userExist){
            startActivity(ChatActivity.createIntent(this, codeResponse.userId))
        }else{
            startActivity(RegistrationActivity.createIntent(this, phone))
        }
    }
}