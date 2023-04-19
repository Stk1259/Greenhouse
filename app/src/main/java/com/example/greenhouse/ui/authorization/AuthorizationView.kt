package com.example.greenhouse.ui.authorization

import com.example.greenhouse.data.remote.response.CodeResponse
import com.example.greenhouse.ui.base.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface AuthorizationView: BaseView {

    @AddToEndSingle
    fun gotConfirmationCode()

    @OneExecution
    fun showMessage(message: Int)

    @AddToEndSingle
    fun onCheckComplete(codeResponse: CodeResponse)
}