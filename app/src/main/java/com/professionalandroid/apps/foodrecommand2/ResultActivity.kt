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

        val randomIndex = Random.nextInt(foodList.size)
        val randomFood = foodList[randomIndex]
        mBinding.tvResult.text = randomFood

        mBinding.btnRestart.setOnClickListener {
            finish()
        }
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
