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

        // View 바인딩을 사용하여 레이아웃의 요소를 코드와 연결합니다.
        mBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        // 질문 레이아웃을 표시합니다. visibility를 이용하여 답변의 가짓수에 따라 변화 되도록 하였습니다.
        mBinding.layoutQuestion.visibility = View.VISIBLE

        // foodDataToText() 메서드를 호출하여 raw 폴더에서 음식 데이터를 읽고, 텍스트로 변환한 후 foodList를 초기화합니다.
        foodDataToText()

        // curQuestion 0으로 시도해보았지만, 계속 오류가 나서 setQuestion으로 첫 번째 질문과 답변 옵션을 따로 구별하였습니다.
        mBinding.tvQuestion.visibility = View.VISIBLE
        setQuestion(
            "나는 ____ 종류의 음식을 먹고 싶다.",
            "한식", "중식", "일식", "양식", "기타", null
        )

        mBinding.btnSubmit.setOnClickListener {
            when (curQuestion) {
                1 -> if (addCondition()) setQuestion(
                    "나는 지금 ____을 먹고 싶다.",
                    "아침", "브런치", "점심", "저녁", null, null
                ) // 2번 질문
                2 -> if (addCondition()) setQuestion(
                    "나는 ____이(가) 들어 갔으면 좋겠다.",
                    "밥", "빵", "면", "고기", "채소", null
                ) // 3번 질문
                3 -> if (addCondition()) setQuestion(
                    "나는 ____요리를 먹고 싶다.",
                    "볶음", "튀김", "구이", "찜(탕)", "날(것)", "기타"
                ) // 4번 질문
                4 -> if (addCondition()) setQuestion(
                    "나는 ____좋아 한다.",
                    "매움", "안매움", null, null, null, null
                ) // 5번 질문
                5 -> if (addCondition()) {
                    // 음식 리스트를 필터링하여 결과 화면으로 이동합니다.
                    // filterFoodList() 함수를 호출하여 음식 데이터를 조건에 따라 필터링하여 resList에 결과를 저장합니다.
                    filterFoodList()
                    // ResultActivity로 전환하기 위한 Intent 객체를 생성합니다.
                    val intent = Intent(this, ResultActivity::class.java)
                    // "foodList"라는 키로 resList를 문자열 배열 리스트 형태로 추가합니다. 이를 통해 ResultActivity로 데이터를 전달합니다.
                    intent.putStringArrayListExtra("foodList", resList)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "답변을 선택해 주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onBackPressed() {
        // 뒤로 가기 버튼으로 인한 오류를 미연에 방지하기 위해 뒤로 가기 버튼을 비활성화하였습니다.
    }

    private fun filterFoodList() {
        for (i in foodList.indices) {
            val foodItem = foodList[i]
            // foodItem 문자열에서 첫 번째 콜론(:) 이전의 부분을 추출하여 foodName 변수에 저장합니다. 이는 foodItem에서 음식 이름을 의미합니다.
            val foodName = foodItem.substring(0, foodItem.indexOf(":"))
            // foodItem.substring(foodItem.indexOf(":") + 1): foodItem 문자열에서 첫 번째 콜론(:) 이후의 부분을 추출하여
            // condition 변수에 저장합니다. 이는 foodItem에서 조건을 의미합니다.
            val condition = foodItem.substring(foodItem.indexOf(":") + 1)
            var conditionMatched = false
            // 선택한 음식 조건에 해당하는지 확인합니다.
            for (j in pickFoodList.indices) {
                if (condition.contains(pickFoodList[j])) {
                    conditionMatched = true
                    break
                }
            }
            // 조건에 해당하는 음식을 결과 리스트에 추가합니다.
            if (conditionMatched) {
                resList.add(foodName)
            }
        }
    }

    private fun addCondition(): Boolean {
        // 선택된 답변 옵션이 없는 경우 false를 반환합니다.
        if (!(mBinding.checkbox1.isChecked || mBinding.checkbox2.isChecked || mBinding.checkbox3.isChecked || mBinding.checkbox4.isChecked || mBinding.checkbox5.isChecked || mBinding.checkbox6.isChecked)) {
            return false
        }
        // 선택된 답변 옵션을 pickFoodList에 추가합니다.
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
        // 다음 질문으로 이동하기 위해 curQuestion 값을 증가시킵니다.
        curQuestion++
        return true
    }

    private fun foodDataToText() {
        // raw 폴더에서 음식 데이터를 읽어와 foodList에 저장합니다.
        // R.raw.food_db로 정의된 raw 폴더의 음식 데이터 파일에 대한 InputStream을 생성합니다.
        val inputStream = resources.openRawResource(R.raw.food_db)
        try {
            // InputStream의 크기에 맞게 ByteArray를 생성합니다.
            val b = ByteArray(inputStream.available())
            // InputStream에서 데이터를 읽어와 ByteArray에 저장합니다.
            inputStream.read(b)
            // ByteArray를 문자열로 변환하여 foodData 변수에 저장합니다.
            val foodData = String(b)
            // 개행 문자(\r\n)를 기준으로 문자열을 분할하여 배열로 변환합니다.
            val res = foodData.split("\r\n".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            // 분할된 문자열 배열을 foodList에 저장합니다.
            foodList = res.asList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setQuestion(
        question: String, answer1: String, answer2: String, answer3: String?,
        answer4: String?, answer5: String?, answer6: String?
    ) {
        // 체크박스 초기화
        mBinding.checkbox1.isChecked = false
        mBinding.checkbox2.isChecked = false
        mBinding.checkbox3.isChecked = false
        mBinding.checkbox4.isChecked = false
        mBinding.checkbox5.isChecked = false
        mBinding.checkbox6.isChecked = false

        // 질문 텍스트 설정
        mBinding.tvQuestion.text = question

        // 답변 옵션 설정
        mBinding.checkbox1.text = answer1
        mBinding.checkbox2.text = answer2
        // answer3~6이 null인 경우에 보이지 않도록 하였습니다.
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
