package com.example.greenhouse.data.remote.request

import com.squareup.moshi.Json

data class Avatar(
    @field:Json(name = "base_64")
    val base_64: String?,
    @field:Json(name = "filename")
    val filename: String?
)