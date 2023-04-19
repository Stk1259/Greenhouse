package com.example.greenhouse.data.remote.request

import com.squareup.moshi.Json

data class ProfileBody(
    @field:Json(name = "avatar")
    val avatar: Avatar,
    @field:Json(name = "birthday")
    val birthday: String?,
    @field:Json(name = "city")
    val city: String?,
    @field:Json(name = "name")
    val name: String?,
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "username")
    val username: String?,
)