package com.example.digitalshop.ui.resetPasword

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.digitalshop.R
import com.example.digitalshop.data.services.model.ResetPasswordRequest
import com.example.digitalshop.data.services.objects.RetrofitInstance
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

class reset : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var sendButton: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset)

        val emailInput = findViewById<EditText>(R.id.txtCorreoCodigo)
        sendButton = findViewById(R.id.btnRegistarRes)
        progressBar = findViewById(R.id.progressBarRegister)

        sendButton.setOnClickListener {
            val email = emailInput.text.toString()

            if (email.isNotEmpty()) {
                // Mostrar loading
                sendButton.isEnabled = false
                sendButton.text = ""
                progressBar.visibility = View.VISIBLE
                sendResetCode(email)
            } else {
                Toasty.info(this, "Por favor ingresa un correo", Toast.LENGTH_SHORT, true)
                    .apply {
                        setGravity(Gravity.BOTTOM, 0, 1800)
                    }.show()
            }
        }
    }

    private fun sendResetCode(email: String) {
        val resetPasswordRequest = ResetPasswordRequest(email)

        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.authService.sendResetCode(resetPasswordRequest)
                if (response.isSuccessful) {
                    sendButton.isEnabled = true
                    sendButton.text = "ENVIAR CODIGO"
                    progressBar.visibility = View.GONE
                    // Código enviado con éxito, redirige a la actividad de verificación del código
                    Toasty.success(this@reset, "el código ah sido enviado", Toast.LENGTH_SHORT, true)
                        .apply {
                            setGravity(Gravity.BOTTOM, 0, 1800)
                        }.show()
                    val intent = Intent(this@reset, codigo::class.java)
                    intent.putExtra("EMAIL_KEY", email)
                    startActivity(intent)
                    finish()
                } else {
                    sendButton.isEnabled = true
                    sendButton.text = "ENVIAR CODIGO"
                    progressBar.visibility = View.GONE
                    Toasty.error(this@reset, "Error al enviar el código", Toast.LENGTH_SHORT, true)
                        .apply {
                            setGravity(Gravity.BOTTOM, 0, 1800)
                        }.show()
                }
            } catch (e: Exception) {
                sendButton.isEnabled = true
                sendButton.text = "ENVIAR CODIGO"
                progressBar.visibility = View.GONE
                Toasty.error(this@reset, "Error de conexión", Toast.LENGTH_SHORT, true)
                    .apply {
                        setGravity(Gravity.BOTTOM, 0, 1800)
                    }.show()
            }
        }
    }

}