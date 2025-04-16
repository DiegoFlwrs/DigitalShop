package com.example.digitalshop.ui.resetPasword

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.digitalshop.R
import com.example.digitalshop.data.services.model.VerifyCodeRequest
import com.example.digitalshop.data.services.objects.RetrofitInstance
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

class codigo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_codigo)

        val emailInput = intent.getStringExtra("EMAIL_KEY")
        val codeInput = findViewById<EditText>(R.id.txtCodigo)
        val verifyButton = findViewById<AppCompatButton>(R.id.btnContinuar)

        verifyButton.setOnClickListener {
            val email = emailInput.toString()
            val code = codeInput.text.toString()

            if (email.isNotEmpty() && code.isNotEmpty()) {
                verifyCode(email, code)
            } else {
                Toasty.success(this, "Por favor ingresa el correo y el código", Toast.LENGTH_SHORT, true)
                    .apply {
                        setGravity(Gravity.BOTTOM, 0, 1800)
                    }.show()
            }
        }
    }
    private fun verifyCode(email: String, code: String) {
        val verifyCodeRequest = VerifyCodeRequest(email, code)

        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.authService.verifyCode(verifyCodeRequest)
                if (response.isSuccessful && response.body()?.valid == true) {
                    Toasty.success(this@codigo, "Código Correcto", Toast.LENGTH_SHORT, true)
                        .apply {
                            setGravity(Gravity.BOTTOM, 0, 1800)
                        }.show()
                    val intent = Intent(this@codigo, restablecerContra::class.java)
                    intent.putExtra("EMAIL_KEY", email)
                    intent.putExtra("CODIGO_KEY", code)
                    startActivity(intent)
                    finish()
                } else {
                    Toasty.error(this@codigo, "Código inválido", Toast.LENGTH_SHORT, true)
                        .apply {
                            setGravity(Gravity.BOTTOM, 0, 1800)
                        }.show()
                }
            } catch (e: Exception) {
                Toasty.error(this@codigo, "Error de conexión", Toast.LENGTH_SHORT, true)
                    .apply {
                        setGravity(Gravity.BOTTOM, 0, 1800)
                    }.show()
            }
        }
    }
}