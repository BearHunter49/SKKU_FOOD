package com.example.skku_food.menuActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.skku_food.R
import com.example.skku_food.adapter.res_rcl_adt
import com.example.skku_food.data.menu_KorTOEng
import com.example.skku_food.data.res_data
import com.example.skku_food.database.DatabaseCopier
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_restaurant.*
import kotlinx.coroutines.*


class RestaurantActivity : AppCompatActivity() {

    private lateinit var mAdView: AdView
    private var job: Job? = null
    private lateinit var menuNM: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        //AdMob
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        mAdView = adView2
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        //from Intent
        menuNM = intent.getStringExtra("menu")
        text_menuNM.text = menuNM

        //학식 or 음식점
        when (menuNM) {
            "학식" -> {
                val items = listOf<res_data>(
                    res_data("학생회관(행단골)", "031-294-8148"),
                    res_data("공대식당(해오름)", "031-290-5442"), res_data("기숙사식당(봉룡학사)", "-")
                )

                res_recyclerview.apply {
                    adapter = res_rcl_adt(items, menuNM)
                    layoutManager = LinearLayoutManager(context)
                }
            }
            else -> {
                dbExecute(menu_KorTOEng.mHash[menuNM])
            }

        }
    }

    // 코루틴 취소
    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }

    //DB에서 음식점 이름, 전화번호만 가져오기
    fun dbExecute(menu_name: String?){
        val query = SimpleSQLiteQuery("SELECT name, phone FROM $menu_name")
        val db = DatabaseCopier.getAppDataBase(context = applicationContext)

        job = CoroutineScope(Dispatchers.IO).launch {
            val resList = db!!.rawDAO().getJustNamePhone(query)
            // UI Thread
            CoroutineScope(Dispatchers.Main).launch {
                res_recyclerview.apply {
                    adapter = res_rcl_adt(resList, menuNM)
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }


}
