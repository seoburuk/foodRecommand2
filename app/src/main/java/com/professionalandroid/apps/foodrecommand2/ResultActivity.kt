package com.professionalandroid.apps.foodrecommand2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.professionalandroid.apps.foodrecommand2.R
import kotlin.random.Random
import com.professionalandroid.apps.foodrecommand2.databinding.ActivityResultBinding as ActivityResultBinding1

class ResultActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityResultBinding1
    private var foodList: List<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view 바인딩을 사용하여 레이아웃의 요소를 코드와 연결합니다.
        mBinding = ActivityResultBinding1.inflate(layoutInflater)
        setContentView(mBinding.root)

        // foodDataToText() 메서드를 호출하여 raw 폴더에서 음식 데이터를 읽고, 텍스트로 변환한 후 foodList를 초기화합니다.
        foodDataToText()

        // foodList에서 무작위 음식을 선택합니다.
        val randomIndex = Random.nextInt(foodList.size)
        val randomFoodItem = foodList[randomIndex]
        val randomFood = randomFoodItem.split(":").firstOrNull() ?: ""
        mBinding.tvResult.text = randomFood + "을 추천드려요"

        // 다른 음식 추천 버튼을 클릭할 때 무작위 음식을 선택합니다.
        mBinding.btnOther.setOnClickListener {
            val randomIndex = Random.nextInt(foodList.size)
            val randomFoodItem = foodList[randomIndex]
            val randomFood = randomFoodItem.split(":").firstOrNull() ?: ""
            mBinding.tvResult.text = randomFood + "을 추천드려요"
        }

        // 다시 시작 버튼을 클릭할 때 MainActivity를 재시작합니다.
        mBinding.btnRestart.setOnClickListener {
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        // 뒤로 가기 버튼으로 인한 오류를 미연에 방지하기 위해 뒤로 가기 버튼을 비활성화하였습니다.
    }

    private fun foodDataToText() {
        val inputStream = resources.openRawResource(R.raw.food_db)
        try {
            // raw 폴더에서 음식 데이터를 읽어와 foodList에 저장합니다.
            val b = ByteArray(inputStream.available())
            inputStream.read(b)
            val companyList = String(b)
            val res = companyList.split("\r\n".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            foodList = res.asList()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "음식 데이터를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}
