package com.example.kouveecustomer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        handler = Handler()
        handler.postDelayed({
            startActivity<MainActivity>()
            finish()
        }, 3000)
    }
}
