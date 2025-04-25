package com.example.digitalshop.data.services.model
import java.io.Serializable
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val imageUrl: String,
    val color: String,
    val size: String,
    val brand: String,
    val categoryId: Int,
    val createdAt: String,
    val updatedAt: String,
    val category: Category
) : Serializable

data class Category(
    val id: Int,
    val name: String
) : Serializable

data class SearchRequest(
    val consulta: String
)


