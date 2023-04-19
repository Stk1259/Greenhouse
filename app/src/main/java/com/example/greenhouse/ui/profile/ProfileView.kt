package com.example.greenhouse.ui.profile

import com.example.greenhouse.data.entity.EntityProfile
import com.example.greenhouse.ui.base.BaseView
import moxy.viewstate.strategy.alias.AddToEndSingle

interface ProfileView: BaseView {
    @AddToEndSingle
    fun showLoading(loading: Boolean)
    @AddToEndSingle
    fun showProfileData(profileData: EntityProfile)
}