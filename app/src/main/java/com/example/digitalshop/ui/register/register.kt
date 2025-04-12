package com.example.digitalshop.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.digitalshop.R
import com.example.digitalshop.data.services.model.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.Toast
import com.example.digitalshop.data.services.objects.RetrofitInstance
import com.example.digitalshop.ui.market.Market
import com.example.digitalshop.ui.principal.inicio

class register : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtConfirmPassword: EditText
    private lateinit var btnRegistrar: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        txtNombre = findViewById(R.id.txtNombre)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtContraseña)
        txtConfirmPassword = findViewById(R.id.txtConfirmContraseña)
        btnRegistrar = findViewById(R.id.btnRegistar)

        btnRegistrar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val correo = txtCorreo.text.toString()
            val password = txtPassword.text.toString()
            val confirm = txtConfirmPassword.text.toString()

            if (password != confirm) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = RegisterRequest(
                name = nombre,
                email = correo,
                password = password
            )

            RetrofitInstance.userService.registerUser(request).enqueue(object : Callback<com.example.digitalshop.data.services.model.RegisterResponse> {
                override fun onResponse(
                    call: Call<com.example.digitalshop.data.services.model.RegisterResponse>,
                    response: Response<com.example.digitalshop.data.services.model.RegisterResponse>
                ) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@register, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@register, inicio::class.java))
                        finish()
                        // Aquí podrías redirigir al login
                    } else {
                        Toast.makeText(this@register, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<com.example.digitalshop.data.services.model.RegisterResponse>, t: Throwable) {
                    Toast.makeText(this@register, "Error de red: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

    }
}