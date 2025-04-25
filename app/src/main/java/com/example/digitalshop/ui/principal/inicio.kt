package com.example.digitalshop.ui.principal

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.digitalshop.R
import com.example.digitalshop.ui.login.login
import com.example.digitalshop.ui.register.register
import es.dmoral.toasty.Toasty

class inicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inicio)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin = findViewById<AppCompatButton>(R.id.btnLogin)
        val btnCrearCuenta = findViewById<AppCompatButton>(R.id.btnCrearCuenta)
        val btnGoogle = findViewById<AppCompatButton>(R.id.btnGoogle)
        val btnFacebook = findViewById<AppCompatButton>(R.id.btnFacebook)
        btnLogin.setOnClickListener {
            startActivity(Intent(this, login::class.java))
        }

        btnCrearCuenta.setOnClickListener {
            startActivity(Intent(this, register::class.java))
        }

        btnGoogle.setOnClickListener {
            Toasty.info(this, "Esta funcionalidad se encuentra en desarollo", Toast.LENGTH_SHORT, true)
                .apply {
                    setGravity(Gravity.BOTTOM, 0, 1800)
                }.show()
        }

        btnFacebook.setOnClickListener {
            Toasty.info(this, "Esta funcionalidad se encuentra en desarollo", Toast.LENGTH_SHORT, true)
                .apply {
                    setGravity(Gravity.BOTTOM, 0, 1800)
                }.show()
        }
    }
}