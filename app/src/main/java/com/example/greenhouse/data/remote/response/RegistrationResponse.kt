package com.example.greenhouse.data.remote.response

import com.squareup.moshi.Json

data class RegistrationResponse(
    @field:Json(name = "refresh_token")
    val refreshToken: String,
    @field:Json(name ="access_token")
    val accessToken: String,
    @field:Json(name = "user_id")
    val userId: Int
)
