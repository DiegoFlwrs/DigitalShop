package com.example.digitalshop.data.services
import com.example.digitalshop.data.services.model.LoginRequest
import com.example.digitalshop.data.services.model.LoginResponse
import com.example.digitalshop.data.services.model.RegisterRequest
import com.example.digitalshop.data.services.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    fun login(@Body credentials: LoginRequest): Call<LoginResponse>
}
