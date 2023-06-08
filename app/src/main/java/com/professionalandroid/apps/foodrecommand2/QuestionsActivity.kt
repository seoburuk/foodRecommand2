package com.professionalandroid.apps.foodrecommand2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.professionalandroid.apps.foodrecommand2.databinding.ActivityQuestionsBinding

class QuestionsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityQuestionsBinding
    private var foodList: List<String> = ArrayList()
    private val pickFoodList = ArrayList<String>()
    private val resList = ArrayList<String>()
    private var curQuestion = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.layoutQuestion.visibility = View.VISIBLE
        foodDataToText()
        mBinding.tvQuestion.visibility = View.VISIBLE

        // Set the first question and answer options
        setQuestion(
            "나는 ____ 종류의 음식을 먹고 싶다.",
            "한식", "중식", "일식", "양식", "기타", null
        )

        mBinding.btnSubmit.setOnClickListener {
            when (curQuestion) {
                1 -> if (addCondition()) setQuestion(
                    "나는 지금 ____을 먹고 싶다.",
                    "아침", "브런치", "점심", "저녁", null, null
                ) //2번
                2 -> if (addCondition()) setQuestion(
                    "나는 ____이(가) 들어 갔으면 좋겠다.",
                    "밥", "빵", "면", "고기", "채소", null
                ) //3번
                3 -> if (addCondition()) setQuestion(
                    "나는 ____요리를 먹고 싶다.",
                    "볶음", "튀김", "구이", "찜(탕)", "날(것)", "기타"
                ) //4번
                4 -> if (addCondition()) setQuestion(
                    "나는 ____좋아 한다.",
                    "매움", "안매움", null, null, null, null
                ) //5번
                5 -> if (addCondition()) {
                    filterFoodList()
                    val intent = Intent(this, ResultActivity::class.java)
                    intent.putStringArrayListExtra("foodList", resList)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "답변을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        // Disable the back button press
    }

    private fun filterFoodList() {
        for (i in foodList.indices) {
            val foodItem = foodList[i]
            val foodName = foodItem.substring(0, foodItem.indexOf(":"))
            val condition = foodItem.substring(foodItem.indexOf(":") + 1)
            var conditionMatched = false
            for (j in pickFoodList.indices) {
                if (condition.contains(pickFoodList[j])) {
                    conditionMatched = true
                    break
                }
            }
            if (conditionMatched) {
                resList.add(foodName)
            }
        }
    }

    private fun addCondition(): Boolean {
        if (!(mBinding.checkbox1.isChecked || mBinding.checkbox2.isChecked || mBinding.checkbox3.isChecked || mBinding.checkbox4.isChecked || mBinding.checkbox5.isChecked || mBinding.checkbox6.isChecked)) {
            return false
        }
        if (mBinding.checkbox1.isChecked) {
            pickFoodList.add(mBinding.checkbox1.text.toString())
        }
        if (mBinding.checkbox2.isChecked) {
            pickFoodList.add(mBinding.checkbox2.text.toString())
        }
        if (mBinding.checkbox3.isChecked) {
            pickFoodList.add(mBinding.checkbox3.text.toString())
        }
        if (mBinding.checkbox4.isChecked) {
            pickFoodList.add(mBinding.checkbox4.text.toString())
        }
        if (mBinding.checkbox5.isChecked) {
            pickFoodList.add(mBinding.checkbox5.text.toString())
        }
        if (mBinding.checkbox6.isChecked) {
            pickFoodList.add(mBinding.checkbox6.text.toString())
        }
        curQuestion++
        return true
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
        }
    }

    private fun setQuestion(
        question: String,
        answer1: String,
        answer2: String,
        answer3: String?,
        answer4: String?,
        answer5: String?,
        answer6: String?
    ) {
        // Reset the checkboxes
        mBinding.checkbox1.isChecked = false
        mBinding.checkbox2.isChecked = false
        mBinding.checkbox3.isChecked = false
        mBinding.checkbox4.isChecked = false
        mBinding.checkbox5.isChecked = false
        mBinding.checkbox6.isChecked = false

        // Set the question text
        mBinding.tvQuestion.text = question

        // Set the answer options
        mBinding.checkbox1.text = answer1
        mBinding.checkbox2.text = answer2

        mBinding.checkbox3.isVisible = answer3 != null
        mBinding.checkbox3.text = answer3

        mBinding.checkbox4.isVisible = answer4 != null
        mBinding.checkbox4.text = answer4

        mBinding.checkbox5.isVisible = answer5 != null
        mBinding.checkbox5.text = answer5

        mBinding.checkbox6.isVisible = answer6 != null
        mBinding.checkbox6.text = answer6
    }
}
