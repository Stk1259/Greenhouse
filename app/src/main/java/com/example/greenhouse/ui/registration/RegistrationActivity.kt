package com.example.greenhouse.ui.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputFilter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.greenhouse.R
import com.example.greenhouse.ui.base.BaseActivity
import com.example.greenhouse.ui.chats.ChatActivity
import moxy.ktx.moxyPresenter


class RegistrationActivity : BaseActivity(), RegistrationView {

    private val registrationPresenter by moxyPresenter {
        RegistrationPresenter(this, intent.getStringExtra(EXTRA_PHONE)!!)
    }

    companion object {
        private const val EXTRA_PHONE = "phone"
        fun createIntent(context: Context, phone: String): Intent {
            return Intent(context, RegistrationActivity::class.java)
                .putExtra(EXTRA_PHONE, phone)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val textNumber = findViewById<TextView>(R.id.textNumber)
        textNumber.text = intent.getStringExtra(EXTRA_PHONE)
        val editName = findViewById<EditText>(R.id.editTextName)
        val editUserName = findViewById<EditText>(R.id.editTextUserName)
        editUserName.filters = arrayOf(filter)
        val button = findViewById<Button>(R.id.buttonRegistration)
        button.setOnClickListener {
            registrationPresenter.register(editName.text.toString(), editUserName.text.toString())
        }
    }

    override fun showMassage(massage: Int) {
        Toast.makeText(
            this, massage, Toast.LENGTH_LONG
        ).show()
    }

    override fun registered(userId: Int) {
        startActivity(ChatActivity.createIntent(this, userId))
    }

    private var filter =
        InputFilter { source, start, end, _, _, _ ->
            for (i in start until end) {
                if (!Character.isLetterOrDigit(source[i]) &&
                    source[i].toString() != "_" &&
                    source[i].toString() != "-"
                ) {
                    return@InputFilter ""
                }
            }
            null
        }
}