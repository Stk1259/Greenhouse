package com.example.greenhouse.data.remote.request

import com.squareup.moshi.Json

data class PhoneRequest(
    @field:Json(name = "phone")
    val phone: String
)
