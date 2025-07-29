package com.example.projectmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TeamMainActivity : AppCompatActivity() {

    private lateinit var btnExistingTeam: Button
    private lateinit var btnJoinTeam : Button
    private lateinit var btnCreateTeam : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnExistingTeam = findViewById<Button>(R.id.btnExistingTeam)
        btnJoinTeam = findViewById<Button>(R.id.btnJoinWithCode)
        btnCreateTeam = findViewById<Button>(R.id.btnCreateTeam)

        btnExistingTeam.setOnClickListener {
            val intent = Intent(this, ExistingTeam::class.java)
            startActivity(intent)
        }

        btnJoinTeam.setOnClickListener {
            val intent = Intent(this, JoinTeam::class.java)
            startActivity(intent)
        }

        btnCreateTeam.setOnClickListener {
            val intent = Intent(this, CreateTeam::class.java)
            startActivity(intent)
        }
    }
}