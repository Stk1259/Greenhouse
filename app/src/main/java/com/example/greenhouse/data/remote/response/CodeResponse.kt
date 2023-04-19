package com.example.greenhouse.data.remote.response

import com.squareup.moshi.Json

data class CodeResponse(
    @field:Json(name = "refresh_token")
    val refreshToken: String?,
    @field:Json(name ="access_token")
    val accessToken: String?,
    @field:Json(name = "user_id")
    val userId: Int,
    @field:Json(name = "is_user_exists")
    val userExist: Boolean
)
