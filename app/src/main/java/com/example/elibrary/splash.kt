package com.example.elibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class splash : AppCompatActivity() {
    private lateinit var imgv:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        imgv =  findViewById(R.id.splash)
        supportActionBar?.hide()
        imgv.alpha = 0f
        imgv.scaleX = 0f
        imgv.scaleY = 0f
        imgv.pivotX = 0.5f
        imgv.pivotY = 0.5f

        imgv.animate().setDuration(2050).alpha(1f).scaleX(1f).scaleY(1f).withEndAction{
            val intent = Intent(this@splash,LogInActivity::class.java)
            finish()
            startActivity(intent)
        }
    }
}