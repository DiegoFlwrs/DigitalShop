package com.example.digitalshop.ui.register

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
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
import es.dmoral.toasty.Toasty

class register : AppCompatActivity() {
    private lateinit var txtNombre: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtPassword: EditText
    private lateinit var txtConfirmPassword: EditText
    private lateinit var btnRegistrar: AppCompatButton
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        txtNombre = findViewById(R.id.txtNombre)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtPassword = findViewById(R.id.txtContraseña)
        txtConfirmPassword = findViewById(R.id.txtConfirmContraseña)
        btnRegistrar = findViewById(R.id.btnRegistar)
        progressBar = findViewById(R.id.progressBarRegister)

        btnRegistrar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val correo = txtCorreo.text.toString()
            val password = txtPassword.text.toString()
            val confirm = txtConfirmPassword.text.toString()

            if (password != confirm) {
                Toasty.error(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT, true)
                    .apply {
                        setGravity(Gravity.BOTTOM, 0, 1800)
                    }.show()
                return@setOnClickListener
            }

            btnRegistrar.isEnabled = false
            btnRegistrar.text = ""
            progressBar.visibility = View.VISIBLE

            val request = RegisterRequest(name = nombre, email = correo, password = password)

            RetrofitInstance.userService.registerUser(request).enqueue(object :
                Callback<com.example.digitalshop.data.services.model.RegisterResponse> {
                override fun onResponse(
                    call: Call<com.example.digitalshop.data.services.model.RegisterResponse>,
                    response: Response<com.example.digitalshop.data.services.model.RegisterResponse>
                ) {
                    btnRegistrar.isEnabled = true
                    btnRegistrar.text = "Registrar"
                    progressBar.visibility = View.GONE

                    if (response.isSuccessful) {
                        Toasty.success(this@register, "Usuario registrado correctamente", Toast.LENGTH_SHORT, true)
                            .apply {
                                setGravity(Gravity.BOTTOM, 0, 1800)
                            }.show()
                        startActivity(Intent(this@register, inicio::class.java))
                        finish()
                    } else {
                        Toasty.error(this@register, "Error: ${response.code()}", Toast.LENGTH_SHORT, true)
                            .apply {
                                setGravity(Gravity.BOTTOM, 0, 1800)
                            }.show()
                    }
                }

                override fun onFailure(
                    call: Call<com.example.digitalshop.data.services.model.RegisterResponse>,
                    t: Throwable
                ) {
                    // Ocultar loading
                    btnRegistrar.isEnabled = true
                    btnRegistrar.text = "Registrar"
                    progressBar.visibility = View.GONE

                    Toasty.error(this@register, "Error de red: ${t.message}", Toast.LENGTH_SHORT, true)
                        .apply {
                            setGravity(Gravity.BOTTOM, 0, 1800)
                        }.show()
                }
            })
        }
    }
}
