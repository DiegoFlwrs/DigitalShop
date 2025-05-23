package com.example.digitalshop.ui.resetPasword

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.digitalshop.R
import com.example.digitalshop.data.services.model.ResetPasswordConfirmationRequest
import com.example.digitalshop.data.services.objects.RetrofitInstance
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch

class restablecerContra : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var btnContinuar: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_restablecer_contra)

        val txtPassword = findViewById<EditText>(R.id.txtContraseña)
        val txtConfirmPassword  = findViewById<EditText>(R.id.txtConfirmarContra)
        btnContinuar = findViewById(R.id.btnContinuar)
        progressBar = findViewById(R.id.progressBarRegister)

        val emailInput = intent.getStringExtra("EMAIL_KEY") ?: ""
        val codeInput = intent.getStringExtra("CODIGO_KEY") ?: ""

        btnContinuar.setOnClickListener {
            val password = txtPassword.text.toString()
            val confirmPassword = txtConfirmPassword.text.toString()

            if (password.isEmpty() || confirmPassword.isEmpty()) {
                Toasty.success(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT, true)
                    .apply {
                        setGravity(Gravity.BOTTOM, 0, 1800)
                    }.show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            btnContinuar.isEnabled = false
            btnContinuar.text = ""
            progressBar.visibility = View.VISIBLE
            resetPassword(emailInput, codeInput, password)
        }
    }

    private fun resetPassword(email: String, code: String, newPassword: String) {
        val resetPasswordRequest = ResetPasswordConfirmationRequest(email, code, newPassword)

        lifecycleScope.launch {
            try {
                val response = RetrofitInstance.authService.resetPassword(resetPasswordRequest)
                if (response.isSuccessful) {
                    btnContinuar.isEnabled = true
                    btnContinuar.text = "CONTINUAR"
                    progressBar.visibility = View.GONE
                    Toasty.success(
                        this@restablecerContra,
                        "Contraseña restablecida con éxito",
                        Toast.LENGTH_SHORT, true)
                        .apply {
                            setGravity(Gravity.BOTTOM, 0, 1800)
                        }.show()
                    finish()
                } else {
                    btnContinuar.isEnabled = true
                    btnContinuar.text = "CONTINUAR"
                    progressBar.visibility = View.GONE
                    Toasty.error(
                        this@restablecerContra,
                        "Error al restablecer la contraseña",
                        Toast.LENGTH_SHORT, true)
                        .apply {
                            setGravity(Gravity.BOTTOM, 0, 1800)
                        }.show()
                }
            } catch (e: Exception) {
                btnContinuar.isEnabled = true
                btnContinuar.text = "CONTINUAR"
                progressBar.visibility = View.GONE
                Toasty.error(
                    this@restablecerContra,
                    "Error de conexión: ${e.message}",
                    Toast.LENGTH_SHORT, true)
                    .apply {
                        setGravity(Gravity.BOTTOM, 0, 1800)
                    }.show()
            }
        }
    }
}