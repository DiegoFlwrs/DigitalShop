package com.example.digitalshop.data.services

import com.example.digitalshop.data.services.model.Product
import com.example.digitalshop.data.services.model.SearchRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProductService {
    @POST("gemini/busqueda")
    fun searchProducts(@Body searchRequest: SearchRequest): Call<List<Product>>
}