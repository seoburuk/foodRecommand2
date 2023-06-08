package com.professionalandroid.apps.foodrecommand2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.app.Activity
import android.content.Intent
import android.widget.ProgressBar
import com.professionalandroid.apps.foodrecommand2.R
import com.professionalandroid.apps.foodrecommand2.databinding.ActivityQuestionsBinding


class QuestionsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityQuestionsBinding
    private var foodList: List<String> = ArrayList()
    private val pickFoodList = ArrayList<String>()
    private val resList = ArrayList<String>()
    private var curQuestion = 1
    private var progressBar: ProgressBar?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.layoutQuestion.visibility = View.VISIBLE
        foodDataToText()
        mBinding.tvQuestion.visibility = View.VISIBLE
        progressBar=findViewById(R.id.progressBar)



        setQuestion(
            "나는 ____ 종류의 음식을 먹고 싶다.",
            "한식", "중식", "일식", "양식", "기타", null
        )


        mBinding.btnSubmit.setOnClickListener { _ ->
            when (curQuestion) {
                1 -> if (addCondition()) setQuestion(
                    "나는 지금 ____을 먹고 싶다.",
                    "아침", "브런치", "점심", "저녁", null, null
                ) //2번
                else Toast.makeText(this, "답변을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                2 -> if (addCondition()) setQuestion(
                    "나는 ____이(가) 들어 갔으면 좋겠다.",
                    "밥", "빵", "면", "고기", "채소", null
                ) //3번
                else Toast.makeText(this, "답변을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                3 -> if (addCondition()) setQuestion(
                    "나는 ____요리를 먹고 싶다.",
                    "볶음", "튀김", "구이", "찜(탕)", "날(것)", "기타"
                ) //4번
                else Toast.makeText(this, "답변을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                4 -> if (addCondition()) setQuestion(
                    "나는 ____좋아 한다.",
                    "매움", "안매움", null, null, null, null
                ) //5번
                else Toast.makeText(this, "답변을 선택해 주세요.", Toast.LENGTH_SHORT).show()
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
        if (!(mBinding.radioButton1.isChecked || mBinding.radioButton2.isChecked || mBinding.radioButton3.isChecked || mBinding.radioButton4.isChecked || mBinding.radioButton5.isChecked || mBinding.radioButton6.isChecked)) {
            return false
        }
        if (mBinding.radioButton1.isChecked) {
            pickFoodList.add(mBinding.radioButton1.text.toString())
        }
        if (mBinding.radioButton2.isChecked) {
            pickFoodList.add(mBinding.radioButton2.text.toString())
        }
        if (mBinding.radioButton3.isChecked) {
            pickFoodList.add(mBinding.radioButton3.text.toString())
        }
        if (mBinding.radioButton4.isChecked) {
            pickFoodList.add(mBinding.radioButton4.text.toString())
        }
        if (mBinding.radioButton5.isChecked) {
            pickFoodList.add(mBinding.radioButton5.text.toString())
        }
        if (mBinding.radioButton6.isChecked) {
            pickFoodList.add(mBinding.radioButton6.text.toString())
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
        mBinding.radioButton1.isChecked = false
        mBinding.radioButton2.isChecked = false
        mBinding.radioButton3.isChecked = false
        mBinding.radioButton4.isChecked = false
        mBinding.radioButton5.isChecked = false
        mBinding.radioButton6.isChecked = false

        mBinding.tvQuestion.text = question
        mBinding.radioButton1.text = answer1
        mBinding.radioButton2.text = answer2

        if (answer3 != null) {
            mBinding.radioButton3.visibility = View.VISIBLE
            mBinding.radioButton3.text = answer3
        } else {
            mBinding.radioButton3.visibility = View.GONE
        }

        if (answer4 != null) {
            mBinding.radioButton4.visibility = View.VISIBLE
            mBinding.radioButton4.text = answer4
        } else {
            mBinding.radioButton4.visibility = View.GONE
        }

        if (answer5 != null) {
            mBinding.radioButton5.visibility = View.VISIBLE
            mBinding.radioButton5.text = answer5
        } else {
            mBinding.radioButton5.visibility = View.GONE
        }

        if (answer6 != null) {
            mBinding.radioButton6.visibility = View.VISIBLE
            mBinding.radioButton6.text = answer6
        } else {
            mBinding.radioButton6.visibility = View.GONE
        }
    }
}
