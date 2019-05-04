package com.example.skku_food.menuActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.skku_food.R
import com.example.skku_food.adapter.ResRclAdt
import com.example.skku_food.data.MenuKorTOEng
import com.example.skku_food.data.ResSimpleData
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
                val items = listOf<ResSimpleData>(
                    ResSimpleData("학생회관(행단골)", "031-294-8148"),
                    ResSimpleData("공대식당(해오름)", "031-290-5442"), ResSimpleData("기숙사식당(봉룡학사)", "-")
                )

                res_recyclerview.apply {
                    adapter = ResRclAdt(items, menuNM)
                    layoutManager = LinearLayoutManager(context)
                }
            }
            else -> {
                dbExecute(MenuKorTOEng.mHash[menuNM])
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
        var resList:List<ResSimpleData>? = null

        job = CoroutineScope(Dispatchers.IO).launch {
            val tempList = db!!.rawDAO().getJustNamePhone(query)
            resList = tempList.toList()
        }

        runBlocking {
            job?.join()
            res_recyclerview.apply {
                adapter = ResRclAdt(resList!!, menuNM)
                layoutManager = LinearLayoutManager(context)
            }
        }

    }


}
