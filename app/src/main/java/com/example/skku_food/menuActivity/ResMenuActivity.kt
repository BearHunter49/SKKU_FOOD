package com.example.skku_food.menuActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skku_food.R
import kotlinx.android.synthetic.main.activity_res_menu.*

class ResMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_menu)

        val resNM = intent.getStringExtra("res_nm")
        text_resNM.text = resNM

        // 학식
        when(resNM){
            "학생회관(행단골)", "공대식당(해오름)", "기숙사식당(봉룡학사)"
                -> CrawlingAsTsk(text_resMenu, this).run {
                execute(resNM)
            }

            else -> print("음식점")

        }

    }
}
