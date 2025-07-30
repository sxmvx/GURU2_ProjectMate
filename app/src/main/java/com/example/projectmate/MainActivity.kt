package com.example.projectmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var loginButton: Button
    private lateinit var teamLoginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        loginButton = findViewById(R.id.LoginButton)
        teamLoginButton = findViewById(R.id.TeamLoginButton)

        loginButton.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        teamLoginButton.setOnClickListener{
            val intent = Intent(this, TeamMainActivity::class.java)
            startActivity(intent)
        }
    }
}