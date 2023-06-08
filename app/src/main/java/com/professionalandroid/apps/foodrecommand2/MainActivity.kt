package com.professionalandroid.apps.foodrecommand2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.professionalandroid.apps.foodrecommand2.QuestionsActivity
import com.professionalandroid.apps.foodrecommand2.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the start button in the layout
        val buttonStart: AppCompatButton = findViewById(R.id.btn_start)

        // Set a click listener for the start button
        buttonStart.setOnClickListener {
            try {
                // Create an intent to start the QuestionsActivity
                val intent = Intent(this@MainActivity, QuestionsActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                // Display an error message if the activity transition fails
                Toast.makeText(this@MainActivity, "액티비티 이동에 실패했습니다.", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }
}
