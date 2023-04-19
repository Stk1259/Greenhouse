package com.example.greenhouse.ui.profile

import android.content.Context
import android.util.Log
import com.example.greenhouse.data.repository.Repository
import com.example.greenhouse.ui.base.BasePresenter
import kotlinx.coroutines.launch
import moxy.presenterScope

class ProfilePresenter(context: Context, val id: Int) : BasePresenter<ProfileView>() {
    private val repository = Repository(context)
    fun getProfileData() = presenterScope.launch {
        viewState.showLoading(false)
        val profileData = repository.getProfileData(id)
        viewState.showLoading(false)
        viewState.showProfileData(profileData)
    }
    fun updateData() = presenterScope.launch {
        viewState.showLoading(false)
        val profileData = repository.updateProfile()
        viewState.showLoading(false)
        viewState.showProfileData(profileData)
    }
}