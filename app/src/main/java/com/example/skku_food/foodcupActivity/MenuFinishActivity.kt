package com.example.skku_food.foodcupActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skku_food.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_menu_finish.*

class MenuFinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_finish)

        val menu = intent.getStringExtra("menu")
        val url = intent.getIntExtra("url", 0)

        text_menuName.text = String.format("당신의 선택은 '$menu'입니다! ")
        imageView.setImageResource(url)

        btn_goResCup.setOnClickListener {
            val intent = Intent(this, ResTournamentActivity::class.java)
            intent.putExtra("menu", menu)
            startActivity(intent)
            finish()
        }

    }
}
