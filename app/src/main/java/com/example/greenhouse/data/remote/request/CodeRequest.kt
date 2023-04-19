package com.example.greenhouse.data.remote.request

import com.squareup.moshi.Json

data class CodeRequest(
    @field:Json(name = "phone")
    val phone: String,
    @field:Json(name = "code")
    val code: String
)
