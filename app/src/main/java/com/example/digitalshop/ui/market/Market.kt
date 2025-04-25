package com.example.digitalshop.ui.market

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.digitalshop.R
import com.example.digitalshop.data.services.model.SearchRequest
import com.example.digitalshop.data.services.objects.RetrofitInstance
import android.content.Intent
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import com.example.digitalshop.data.services.model.Product
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Market : AppCompatActivity() {
    private lateinit var editTextBuscar: EditText
    private lateinit var btnBuscar: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_market)

        editTextBuscar = findViewById(R.id.txtBuscar)
        btnBuscar = findViewById(R.id.btnBuscar)

        btnBuscar.setOnClickListener {
            val query = editTextBuscar.text.toString()
            if (query.isNotEmpty()) {
                searchProducts(query)
            }
        }
    }

    private fun searchProducts(query: String) {
        val searchRequest = SearchRequest(query)
        val call = RetrofitInstance.productService.searchProducts(searchRequest)

        call.enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val products = response.body()

                    if (products != null && products.isNotEmpty()) {
                        val intent = Intent(this@Market, Results::class.java)
                        intent.putExtra("PRODUCTS_LIST", ArrayList(products))
                        startActivity(intent)
                    } else {
                        Toasty.info(this@Market, "No se encontraron productos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toasty.warning(this@Market, "Error en la búsqueda: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toasty.error(
                    this@Market,
                    "Error al buscar productos: ${t.localizedMessage ?: "Error desconocido"}",
                    Toast.LENGTH_LONG,
                    true
                ).show()
            }
        })
    }

}