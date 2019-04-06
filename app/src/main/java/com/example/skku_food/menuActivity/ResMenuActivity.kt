package com.example.skku_food.menuActivity

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.skku_food.R
import com.example.skku_food.adapter.res_rcl_adt
import com.example.skku_food.data.menuKor_Eng
import com.example.skku_food.data.res_menu_data
import com.example.skku_food.database.DatabaseCopier
import kotlinx.android.synthetic.main.activity_res_menu.*
import kotlinx.android.synthetic.main.activity_restaurant.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ResMenuActivity : AppCompatActivity() {

    private var mAsTsk: CrawlingAsTsk? = null
    private var job: Job? = null
    private var resInfo: res_menu_data? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_menu)

        val resNM = intent.getStringExtra("name")
        val menuNM = intent.getStringExtra("menu")
        text_resNM.text = resNM


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
                dbExecute(menuKor_Eng.mHash[menuNM], resNM)
            }

        }

        // Button
        btn_location.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.putExtra("latitude", resInfo?.latitude)
            intent.putExtra("longitude", resInfo?.longitude)
            intent.putExtra("name", resInfo?.name)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        // AsyncTask 실행을 종료시켜 줌
        if(mAsTsk?.status == AsyncTask.Status.RUNNING)
            mAsTsk?.cancel(true)

        super.onDestroy()
    }

    //DB에서 음식점 정보 가져오기
    fun dbExecute(menu_name: String?, res_name: String){
        val query = SimpleSQLiteQuery("SELECT * FROM $menu_name where name = '$res_name'")
        val db = DatabaseCopier.getAppDataBase(context = applicationContext)

        job = CoroutineScope(Dispatchers.IO).launch {
            resInfo = db!!.rawDAO().getFullResInfo(query)
            // UI Thread
            CoroutineScope(Dispatchers.Main).launch {
                text_resMenu.text = resInfo?.menu
                text_phone.text = resInfo?.phone
            }
        }

    }
}
