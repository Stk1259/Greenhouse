package com.example.greenhouse.data.api

import com.example.greenhouse.data.remote.request.*
import com.example.greenhouse.data.remote.response.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT

private const val BASE_URL = "https://plannerok.ru/"
val apiService: ApiService = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()
    .create(ApiService::class.java)

interface ApiService {
    @POST("api/v1/users/send-auth-code/")
    suspend fun sendNumber(@Body request: PhoneRequest): PhoneResponse
    @POST("api/v1/users/send-auth-code/")
    suspend fun sendAuthCode(@Body request: CodeRequest): CodeResponse
    @POST("api/v1/users/register/")
    suspend fun register(@Body request: RegistrationRequest): RegistrationResponse
    @POST("api/v1/users/refresh-token/")
    suspend fun refreshToken(@Body request: RefreshToken): AccessToken
    @PUT("api/v1/users/me/")
    suspend fun putProfile(@Body body: ProfileBody, @HeaderMap headers: Map<String, String?>)
    @GET("api/v1/users/me/")
    suspend fun getProfile(@HeaderMap headers: Map<String, String?>): ProfileData
}
