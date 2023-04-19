package com.example.greenhouse.ui.registration

import com.example.greenhouse.ui.base.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution

interface RegistrationView: BaseView {
    @OneExecution
    fun showMassage(massage: Int)

    @AddToEndSingle
    fun registered(userId: Int)
}
