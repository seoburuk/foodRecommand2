package com.professionalandroid.apps.foodrecommand2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStart:Button = findViewById(R.id.btn_start)
        buttonStart.setOnClickListener {
                val intent = Intent(this@MainActivity, QuestionsActivity::class.java)
                // TODO Pass the name through intent using the constant variable which we have created.
                startActivity(intent)
                finish()
            }
        }
    }
