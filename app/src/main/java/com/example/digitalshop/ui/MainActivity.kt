package com.example.digitalshop.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.animation.ValueAnimator
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalshop.R
import com.example.digitalshop.ui.principal.inicio

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.txtBienvenido)
        val text = "DIGITAL SHOP"

        textView.text = ""
        val animator = ValueAnimator.ofInt(0, text.length)
        animator.duration = 1500
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            val index = animation.animatedValue as Int
            textView.text = text.substring(0, index)
        }
        animator.start()

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, inicio::class.java))
            finish()
        }, 3000)
    }
}
