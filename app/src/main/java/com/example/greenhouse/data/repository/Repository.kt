package com.example.greenhouse.data.repository

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.greenhouse.data.DatabaseModule
import com.example.greenhouse.data.api.apiService
import com.example.greenhouse.data.dao.ProfileDao
import com.example.greenhouse.data.entity.EntityProfile
import com.example.greenhouse.data.preferences.TokenManagerImpl
import com.example.greenhouse.data.preferences.TokenUpdater
import com.example.greenhouse.data.remote.request.*
import com.example.greenhouse.data.remote.response.AccessToken
import com.example.greenhouse.data.remote.response.CodeResponse
import com.example.greenhouse.data.remote.response.PhoneResponse
import com.example.greenhouse.data.remote.response.RegistrationResponse

class Repository(context: Context) : ProfileDao {

    companion object {
        private const val HEADER = "Authorization"
    }

    private val db by lazy {
        Room.databaseBuilder(context, DatabaseModule::class.java, "database").build()
    }
    private val tokenManager: TokenManagerImpl = TokenManagerImpl(context)

    suspend fun sendNumber(phoneNumber: String): PhoneResponse {
        return apiService.sendNumber(PhoneRequest(phoneNumber))
    }

    suspend fun sendAuthCode(code: String, phoneNumber: String): CodeResponse {
        val codeResponse = apiService.sendAuthCode(CodeRequest(phoneNumber, code))
        Log.d("codeResponse", codeResponse.toString())
        if (codeResponse.refreshToken != null && codeResponse.accessToken != null) {
            saveAccessToken(codeResponse.accessToken)
            saveRefreshToken(codeResponse.refreshToken)
            val tokenUpdater = TokenUpdater(tokenManager)
        }
        return codeResponse
    }

    suspend fun register(phone: String, name: String, userName: String): RegistrationResponse {
        val codeResponse = apiService.register(RegistrationRequest(phone, name, userName))
        Log.d("codeResponse", codeResponse.toString())
        saveAccessToken(codeResponse.accessToken)
        saveRefreshToken(codeResponse.refreshToken)
        val tokenUpdater = TokenUpdater(tokenManager)
//        setProfile(codeResponse.userId, name, null, null, null, null, null, userName)
        return codeResponse
    }

    private fun saveAccessToken(token: String) {
        tokenManager.saveAccessToken(token)
    }

    private fun getAccessToken(): String? {
        return tokenManager.getAccessToken()
    }

    private fun saveRefreshToken(token: String) {
        tokenManager.saveRefreshToken(token)
    }

    suspend fun refreshToken(token: RefreshToken): AccessToken {
        return apiService.refreshToken(token)
    }

    override suspend fun getProfileData(id: Int): EntityProfile {
        return db.profileDao.getProfileData(id)
    }

    override suspend fun insertRegistration(
        id: Int,
        phone: String,
        name: String,
        userName: String,
    ) {
        db.profileDao.insertRegistration(id, phone, name, userName)
    }


    suspend fun setProfile(
        id: Int,
        name: String?,
        city: String?,
        birthday: String?,
        about: String?,
        avatar: String?,
        avatarName: String?,
        username: String,
    ) {
        if (name != null) {
            updateName(id, name)
        }
        if (city != null) {
            updateCity(id, city)
        }
        if (birthday != null) {
            updateBirthday(id, birthday)
        }
        if (about != null) {
            updateAbout(id, about)
        }
        if (avatar != null && avatarName != null) {
            updateAvatar(id, avatar, avatarName)
        }
        val header = mapOf(HEADER to "Bearer ${getAccessToken()}")
        try{
        apiService.putProfile(
            ProfileBody(
                Avatar(avatar, avatarName),
                birthday,
                city,
                name,
                about,
                username), header
        )}catch (e: Exception)
        {Log.e(e.cause.toString(), e.message.toString())}
    }

    suspend fun updateProfile(): EntityProfile {
        val header = mapOf(HEADER to "Bearer ${getAccessToken()}")
        val response = apiService.getProfile(header)
        Log.d("GET", response.toString())
        return EntityProfile(
            response.id,
            response.avatar,
            response.birthday,
            response.city,
            response.name,
            response.phone,
            response.status,
            response.username,
            response.avatars?.avatar
        )
    }

    override suspend fun updateName(id: Int, name: String) {
        db.profileDao.updateName(id, name)
    }

    override suspend fun updateCity(id: Int, city: String) {
        db.profileDao.updateCity(id, city)
    }

    override suspend fun updateBirthday(id: Int, birthday: String) {
        db.profileDao.updateBirthday(id, birthday)
    }

    override suspend fun updateAbout(id: Int, about: String) {
        db.profileDao.updateAbout(id, about)
    }

    override suspend fun updateAvatar(id: Int, avatar: String, avatarName: String) {
        db.profileDao.updateAvatar(id, avatar, avatarName)
    }
}