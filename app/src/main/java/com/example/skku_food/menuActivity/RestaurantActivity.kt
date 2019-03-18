package com.example.skku_food.menuActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skku_food.R
import com.example.skku_food.adapter.res_rcl_adt
import com.example.skku_food.data.res_data
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_restaurant.*

class RestaurantActivity : AppCompatActivity() {

    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        //AdMob
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        mAdView = adView2
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //from Intent
        val menuNM = intent.getStringExtra("menu")
        text_menuNM.text = menuNM

        //학식 or 음식점
        if (menuNM == "학식"){
            val items = arrayListOf(res_data("학생회관(행단골)", "031-294-8148"),
                res_data("공대식당(해오름)", "031-290-5442"), res_data("기숙사식당(봉룡학사)", "-"))

            res_recyclerview.apply {
                adapter = res_rcl_adt(items)
                layoutManager = LinearLayoutManager(context)
            }

        }else{
            //DB에서 음식점 이름만 가져오기

        }
    }





}
