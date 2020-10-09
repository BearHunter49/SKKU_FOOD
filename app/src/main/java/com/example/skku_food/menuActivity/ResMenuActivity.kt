package com.example.skku_food.menuActivity

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import androidx.core.text.HtmlCompat
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.skku_food.R
import com.example.skku_food.data.MenuKorTOEng
import com.example.skku_food.data.ResFullData
import com.example.skku_food.database.DatabaseCopier
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_res_menu.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ResMenuActivity : AppCompatActivity() {

    private var mAsTsk: CrawlingAsTsk? = null
    private var job: Job? = null
    private var resInfo: ResFullData? = null
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_menu)

        //AdMob
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        mAdView = adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        val resNM = intent.getStringExtra("name")
        val menuNM = intent.getStringExtra("menu")
        val phoneNB = intent.getStringExtra("phone")
        text_resNM.text = resNM
        text_phone.text = phoneNB


        when(resNM){
            // 학식
            "학생회관(행단골)", "공대식당(해오름)", "기숙사식당(봉룡학사)"
                -> {
                btn_location.visibility = View.INVISIBLE
                mAsTsk = CrawlingAsTsk(text_resMenu, this, this)
                mAsTsk?.execute(resNM)
            }
            // 음식점
            else -> {
                dbExecute(MenuKorTOEng.mHash[menuNM], resNM)
            }

        }

        // Button
        btn_location.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("latitude", resInfo!!.latitude)
            intent.putExtra("longitude", resInfo!!.longitude)
            intent.putExtra("name", resInfo!!.name)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        // AsyncTask 종료
        if(mAsTsk?.status == AsyncTask.Status.RUNNING)
            mAsTsk?.cancel(true)
        // 코루틴 종료
        job?.cancel()
        super.onDestroy()
    }

    //DB에서 음식점 정보 가져오기
    fun dbExecute(menu_name: String?, res_name: String){
        val query = SimpleSQLiteQuery("SELECT * FROM $menu_name where name = '$res_name'")
        val db = DatabaseCopier.getAppDataBase(context = applicationContext)

        job = CoroutineScope(Dispatchers.IO).launch {
            resInfo = db!!.rawDAO().getFullResInfo(query)
            Log.d("GPS", resInfo!!.latitude)
            Log.d("GPS", resInfo!!.longitude)

            val menuList = resInfo!!.menu.split(",")
            var resultStr = "<h4><font color='black'>메뉴</font></h4>"
            for (menu in menuList){
                resultStr += String.format("%s<br>", menu)
            }
            // UI Thread
            CoroutineScope(Dispatchers.Main).launch {
                text_resMenu.text = Html.fromHtml(resultStr, HtmlCompat.FROM_HTML_MODE_LEGACY)
            }
        }

    }
}
