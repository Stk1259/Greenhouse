package com.example.greenhouse.ui.profile_settings

import android.content.Context
import com.example.greenhouse.data.repository.Repository
import com.example.greenhouse.ui.base.BasePresenter
import kotlinx.coroutines.launch
import moxy.presenterScope

class SettingsPresenter(context: Context, val id: Int): BasePresenter<SettingView>(){
    private val repository = Repository(context)
    fun getProfileData() = presenterScope.launch{
        viewState.showLoading(false)
        val profileData = repository.getProfileData(id)
        viewState.showProfileData(profileData)
    }
    fun setProfile(
        id: Int,
        name: String?,
        city: String?,
        birthday: String?,
        about: String?,
        avatar: String,
        avatarName: String,
        username: String
    ) = presenterScope.launch {
        viewState.showLoading(true)
        repository.setProfile(id, name, city, birthday, about, avatar, avatarName, username)
        val profileData = repository.getProfileData(id)
        viewState.showLoading(false)
        viewState.showProfileData(profileData)
    }
}