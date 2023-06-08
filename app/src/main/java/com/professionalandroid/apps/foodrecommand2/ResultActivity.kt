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
        mBinding = ActivityResultBinding1.inflate(layoutInflater)
        setContentView(mBinding.root)
        foodDataToText()

        // Choose a random food from the foodList
        val randomIndex = Random.nextInt(foodList.size)
        val randomFoodItem = foodList[randomIndex]
        val randomFood = randomFoodItem.split(":").firstOrNull() ?: ""
        mBinding.tvResult.text = randomFood + "을 추천드려요"

        mBinding.btnOther.setOnClickListener {
            val randomIndex = Random.nextInt(foodList.size)
            val randomFoodItem = foodList[randomIndex]
            val randomFood = randomFoodItem.split(":").firstOrNull() ?: ""
            mBinding.tvResult.text = randomFood + "을 추천드려요"
        }

        mBinding.btnRestart.setOnClickListener {
            // Restart the MainActivity
            val intent = Intent(this@ResultActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onBackPressed() {
        // Disable the back button press
    }

    private fun foodDataToText() {
        val inputStream = resources.openRawResource(R.raw.food_db)
        try {
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
