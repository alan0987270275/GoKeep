package com.example.gokeep.view.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gokeep.util.PreferencesManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val prefManager = PreferencesManager
        prefManager.init(this)
        when (prefManager.get("first_time_in_app", false)) {
            true -> startActivity(Intent(this, MainActivity::class.java))
            false -> startActivity(Intent(this, TutorialActivity::class.java))
        }
        finish()
    }
}