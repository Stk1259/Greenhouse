package com.example.greenhouse.data.remote.response

import com.example.greenhouse.data.remote.request.Avatar
import com.squareup.moshi.Json

data class ProfileData(
    @field:Json(name = "avatars")
    val avatars: Avatars?,
    @field:Json(name = "birthday")
    val birthday: String?,
    @field:Json(name = "city")
    val city: String?,
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "phone")
    val phone: String,
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "username")
    val username: String,
    @field:Json(name = "avatar")
    val avatar: String?
)