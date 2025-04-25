package com.example.digitalshop.ui.market

import ProductAdapter
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.digitalshop.R
import androidx.recyclerview.widget.RecyclerView
import com.example.digitalshop.data.services.model.Product
import es.dmoral.toasty.Toasty


class Results : AppCompatActivity() {
    private lateinit var recyclerViewResults: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("diego", "Results Activity Created")
        enableEdgeToEdge()
        setContentView(R.layout.activity_results)

        recyclerViewResults = findViewById(R.id.recyclerViewResults)

        val products = intent.getSerializableExtra("PRODUCTS_LIST") as ArrayList<Product>
        productAdapter = ProductAdapter(products)
        recyclerViewResults.layoutManager = LinearLayoutManager(this)
        recyclerViewResults.adapter = productAdapter

    }
}