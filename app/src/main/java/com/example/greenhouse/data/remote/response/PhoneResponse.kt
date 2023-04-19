package com.example.greenhouse.data.remote.response

import com.squareup.moshi.Json

data class PhoneResponse(
    @field:Json(name = "is_success")
    val success: Boolean
)