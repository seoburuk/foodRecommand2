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

        // 레이아웃에서 시작 버튼을 찾습니다.
        val buttonStart: AppCompatButton = findViewById(R.id.btn_start)

        // 시작 버튼에 클릭 리스너를 설정합니다.
        buttonStart.setOnClickListener {
            try {
                // QuestionsActivity를 시작하기 위한 인텐트를 생성합니다.
                val intent = Intent(this@MainActivity, QuestionsActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                // 액티비티 전환에 실패한 경우 에러 메시지를 토스트로 표시합니다.

                // 에러 메시지를 생성하여 토스트로 표시합니다.
                Toast.makeText(this@MainActivity, "액티비티 이동에 실패했습니다.", Toast.LENGTH_SHORT).show()

                // 예외의 스택 트레이스를 출력합니다.
                e.printStackTrace()
            }
        }
    }
}
