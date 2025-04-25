package com.example.digitalshop.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.digitalshop.R
import android.widget.Toast
import com.example.digitalshop.data.services.model.LoginRequest
import com.example.digitalshop.data.services.model.LoginResponse
import com.example.digitalshop.data.services.objects.RetrofitInstance
import com.example.digitalshop.ui.market.Market
import com.example.digitalshop.ui.principal.inicio
import com.example.digitalshop.ui.resetPasword.reset
import es.dmoral.toasty.Toasty
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<AppCompatButton>(R.id.loginButton)
        val resetPassword = findViewById<TextView>(R.id.txtResetPassword)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        resetPassword.setOnClickListener {
            startActivity(Intent(this, reset::class.java))
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Mostrar solo el spinner y deshabilitar el botón
                loginButton.isEnabled = false
                loginButton.text = ""
                progressBar.visibility = View.VISIBLE

                loginUser(email, password, loginButton, progressBar)
            } else {
                Toasty.info(this, "Por favor, ingresa tus credenciales", Toast.LENGTH_SHORT, true)
                    .apply {
                        setGravity(Gravity.BOTTOM, 0, 1800)
                    }.show()
            }
        }
    }

    private fun loginUser(
        email: String,
        password: String,
        loginButton: AppCompatButton,
        progressBar: ProgressBar
    ) {
        val loginRequest = LoginRequest(email, password)
        val call = RetrofitInstance.authService.login(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                loginButton.isEnabled = true
                loginButton.text = "Ingresar"
                progressBar.visibility = View.GONE

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.let {
                        saveToken(it.access_token)
                        Toasty.success(this@login, "Bienvenido, ${it.user.email}", Toast.LENGTH_SHORT, true)
                            .apply {
                                setGravity(Gravity.BOTTOM, 0, 1800)
                            }.show()
                        startActivity(Intent(this@login, Market::class.java))
                        finish()
                    }
                } else {
                    Toasty.error(this@login, "Credenciales inválidas", Toast.LENGTH_SHORT, true)
                        .apply {
                            setGravity(Gravity.BOTTOM, 0, 1800)
                        }.show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginButton.isEnabled = true
                loginButton.text = "Ingresar"
                progressBar.visibility = View.GONE

                Toasty.error(this@login, "Error: ${t.message}", Toast.LENGTH_SHORT, true)
                    .apply {
                        setGravity(Gravity.BOTTOM, 0, 1800)
                    }.show()
            }
        })
    }

    private fun saveToken(token: String) {
        val sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("JWT_TOKEN", token)
        editor.apply()
    }
}