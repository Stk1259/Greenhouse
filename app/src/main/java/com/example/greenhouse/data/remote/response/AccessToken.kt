package com.example.greenhouse.data.remote.response

import com.squareup.moshi.Json

data class AccessToken(
    @field:Json(name = "access_token")
    val accessToken: String
)