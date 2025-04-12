package com.example.digitalshop.data.services.objects
import com.example.digitalshop.data.services.AuthService
import com.example.digitalshop.data.services.UserService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://192.168.1.8:3000/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val authService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }
    val userService: UserService by lazy {
        retrofit.create(UserService::class.java)
    }
}