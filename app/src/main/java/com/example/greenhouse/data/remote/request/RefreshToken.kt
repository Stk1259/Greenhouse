package com.example.greenhouse.data.remote.request

import com.squareup.moshi.Json

data class RefreshToken(
    @field:Json(name = "refresh_token")
    val refreshToken: String
)