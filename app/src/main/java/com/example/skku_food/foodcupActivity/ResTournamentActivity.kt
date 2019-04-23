package com.example.skku_food.foodcupActivity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.skku_food.R
import com.example.skku_food.data.menu_KorTOEng
import com.example.skku_food.data.res_data
import com.example.skku_food.database.DatabaseCopier
import kotlinx.android.synthetic.main.activity_res_tournament.*
import kotlinx.coroutines.*

class ResTournamentActivity : AppCompatActivity() {

    private lateinit var model: ResViewModel
    private lateinit var job: Job
    private lateinit var startList: MutableList<res_data>
    private var endList:MutableList<res_data> = mutableListOf()
    private var totalCount = 0
    private var count = 1
    private var round = 1
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_tournament)

        dialog = ProgressDialog(this)
        val menu = intent.getStringExtra("menu")
        val menuEngName = menu_KorTOEng.mHash[menu]

        // 다이얼로그 띄우기
        dialog.apply {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setMessage("DB 읽는 중...")
            setCanceledOnTouchOutside(false)
            setOnCancelListener {
                finish()
            }
            show()
        }

        // ViewModel 달기
        model = ViewModelProviders.of(this).get(ResViewModel::class.java)
        val resObserver = Observer<List<res_data>>{ newList ->
            text_res1.text = newList[0].name
            text_res2.text = newList[1].name
        }
        model.currentRes.observe(this, resObserver)


        // DB에서 res_data 뽑아오기
        val query = SimpleSQLiteQuery("SELECT name, phone FROM $menuEngName")
        val db = DatabaseCopier.getAppDataBase(context = applicationContext)
        job = CoroutineScope(Dispatchers.IO).launch {
            val resList = db!!.rawDAO().getJustNamePhone(query)
            startList = resList.toMutableList()
        }

        runBlocking {
            job.join()
            dialog.dismiss()
        }

        // Initial state
        totalCount = startList.size / 2
        val firstItem = startList.random()
        startList.remove(firstItem)
        val secondItem = startList.random()
        startList.remove(secondItem)
        model.currentRes.value = listOf(firstItem, secondItem)
        text_round.text = String.format("$round 라운드 $count / $totalCount")



        // --------------------Button Click----------------------------------
        text_res1.setOnClickListener {
            endList.add(model.currentRes.value!![0])
            count++

            // 1개 남았으면 -> 부전승
            if (startList.size == 1){
                endList.add(startList[0])
                startList.removeAt(0)
            }

            // 현재 경기를 마쳤을 경우
            if (startList.size == 0){
                // 경기가 더 남은 경우 -> startList 와 endList 바꿔주기
                if (endList.size > 1){
                    startList = endList.toMutableList()
                    endList.clear()
                    round++
                    totalCount = startList.size / 2
                    count = 1
                }

                // 경기가 끝인 경우 -> 끝내기
                else{
                    val intent = Intent(this, ResFinishActivity::class.java)
                    intent.putExtra("menu", menu)
                    intent.putExtra("resName", endList[0].name)
                    startActivity(intent)
                    finish()
                }
            }

            // 다음 메뉴 뽑기 (safely)
            if (startList.size > 1){
                val first = startList.random()
                startList.remove(first)
                val second = startList.random()
                startList.remove(second)
                model.currentRes.value = listOf(first, second)
                text_round.text = String.format("$round 라운드 $count / $totalCount")
            }

        }

        text_res2.setOnClickListener {
            endList.add(model.currentRes.value!![1])
            count++

            // 1개 남았으면 -> 부전승
            if (startList.size == 1){
                endList.add(startList[0])
                startList.removeAt(0)
            }

            // 현재 경기를 마쳤을 경우
            if (startList.size == 0){
                // 경기가 더 남은 경우 -> startList 와 endList 바꿔주기
                if (endList.size > 1){
                    startList = endList.toMutableList()
                    endList.clear()
                    round++
                    totalCount = startList.size / 2
                    count = 1
                }

                // 경기가 끝인 경우 -> 끝내기
                else{
                    val intent = Intent(this, ResFinishActivity::class.java)
                    intent.putExtra("menu", menu)
                    intent.putExtra("resName", endList[0].name)
                    startActivity(intent)
                    finish()
                }
            }

            // 다음 메뉴 뽑기 (safely)
            if (startList.size > 1){
                val first = startList.random()
                startList.remove(first)
                val second = startList.random()
                startList.remove(second)
                model.currentRes.value = listOf(first, second)
                text_round.text = String.format("$round 라운드 $count / $totalCount")
            }
        }

    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


}
