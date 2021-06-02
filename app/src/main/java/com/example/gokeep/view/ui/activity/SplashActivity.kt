package com.example.gokeep.view.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gokeep.util.PreferencesManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialize PreferencesManager with applicationContext
        PreferencesManager.init(applicationContext)
        when (PreferencesManager.get("first_time_in_app", false)) {
            true -> startActivity(Intent(this, MainActivity::class.java))
            false -> startActivity(Intent(this, OnBoardingActivity::class.java))
        }
        finish()
    }
}