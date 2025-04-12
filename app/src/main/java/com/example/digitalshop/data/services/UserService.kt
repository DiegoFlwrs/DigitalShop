package com.example.digitalshop.data.services

import com.example.digitalshop.data.services.model.RegisterRequest
import com.example.digitalshop.data.services.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("users")
    fun registerUser(@Body user: RegisterRequest): Call<RegisterResponse>
}