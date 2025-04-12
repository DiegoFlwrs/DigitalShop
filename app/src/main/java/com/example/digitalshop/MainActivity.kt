package com.example.digitalshop

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalshop.principal.inicio

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.txtBienvenido) // Asegúrate de que el ID sea correcto
        val text = "Bienvenido a DigitalShop"

        // Animación de texto: Mostrar letra por letra
        textView.text = ""
        val animator = ValueAnimator.ofInt(0, text.length)
        animator.duration = 2000 // Duración total de la animación (2 segundos)
        animator.interpolator = LinearInterpolator()  // Hacer la animación más fluida
        animator.addUpdateListener { animation ->
            val index = animation.animatedValue as Int
            textView.text = text.substring(0, index)
        }
        animator.start()

        // Después de que la animación termine, navegar a la siguiente actividad
        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, inicio::class.java))
            finish()  // Finaliza la MainActivity para evitar que el usuario regrese
        }, 3000) // El mismo tiempo de duración de la animación
    }
}
