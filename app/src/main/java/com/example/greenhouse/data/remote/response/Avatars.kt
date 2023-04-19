package com.example.greenhouse.data.remote.response

import com.squareup.moshi.Json

data class Avatars(
    @field:Json(name = "avatar")
    val avatar: String?,
)