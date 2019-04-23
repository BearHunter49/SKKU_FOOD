package com.example.skku_food.foodcupActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skku_food.R
import com.example.skku_food.menuActivity.ResMenuActivity
import kotlinx.android.synthetic.main.activity_res_finish.*

class ResFinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_finish)

        val menu = intent.getStringExtra("menu")
        val resName = intent.getStringExtra("resName")

        text_result.text = String.format("당신의 최종 선택은 '$menu' 의 '$resName' 입니다!")

        btn_goResMenu.setOnClickListener {
            val intent = Intent(this, ResMenuActivity::class.java)
            intent.putExtra("menu", menu)
            intent.putExtra("name", resName)
            startActivity(intent)
            finish()
        }


    }
}
