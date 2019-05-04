package com.example.skku_food.foodcupActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skku_food.R
import com.example.skku_food.menuActivity.ResMenuActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_res_finish.*

class ResFinishActivity : AppCompatActivity() {

    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_finish)

        //AdMob
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        mAdView = adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

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
