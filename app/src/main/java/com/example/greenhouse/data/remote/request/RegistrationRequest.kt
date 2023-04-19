package com.example.greenhouse.data.remote.request

import com.squareup.moshi.Json

data class RegistrationRequest(
    @field:Json(name = "phone")
    val phone: String,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "username")
    val userName: String
)
