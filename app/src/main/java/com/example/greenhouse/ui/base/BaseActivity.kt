package com.example.greenhouse.ui.base

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.example.greenhouse.ui.base.BaseView
import moxy.MvpAppCompatActivity

open class BaseActivity: MvpAppCompatActivity(), BaseView {
    protected fun hideKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}