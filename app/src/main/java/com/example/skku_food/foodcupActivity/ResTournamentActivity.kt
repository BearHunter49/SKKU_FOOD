package com.example.skku_food.foodcupActivity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.skku_food.R
import com.example.skku_food.data.MenuKorTOEng
import com.example.skku_food.data.ResSimpleData
import com.example.skku_food.database.DatabaseCopier
import kotlinx.android.synthetic.main.activity_res_tournament.*
import kotlinx.coroutines.*
import kotlin.random.Random

class ResTournamentActivity : AppCompatActivity() {

    private lateinit var model: ResViewModel
    private lateinit var job: Job
    private lateinit var startList: MutableList<ResSimpleData>
    private var endList:MutableList<ResSimpleData> = mutableListOf()
    private var totalCount = 0
    private var count = 1
    private var round = 1
    private var _position = 0
    private lateinit var menu: String
    private lateinit var dialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_tournament)

        dialog = ProgressDialog(this)
        menu = intent.getStringExtra("menu")
        val menuEngName = MenuKorTOEng.mHash[menu]

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
        val resObserver = Observer<List<ResSimpleData>>{ newList ->
            text_res1.text = newList[0].name
            text_res2.text = newList[1].name
        }
        model.currentRes.observe(this, resObserver)


        // DB에서 ResSimpleData 뽑아오기
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

        // Anim
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.tournament_select)
        myAnim.setAnimationListener(object : Animation.AnimationListener{
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                when(_position){
                    0  -> tournament(0)
                    1  -> tournament(1)
                }
                image_vs.bringToFront()
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })



        // --------------------Button Click----------------------------------
        text_res1.setOnClickListener {
            _position = 0
            it.bringToFront()
            it.startAnimation(myAnim)
        }

        text_res2.setOnClickListener {
            _position = 1
            it.bringToFront()
            it.startAnimation(myAnim)
        }

        btn_random.setOnClickListener {
            _position = Random.nextInt(2)
            when(_position){
                0 -> text_res1.apply {
                    bringToFront()
                    startAnimation(myAnim)
                }
                1 -> text_res2.apply {
                    bringToFront()
                    startAnimation(myAnim)
                }
            }
        }

    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }


    fun tournament(position:Int){
        endList.add(model.currentRes.value!![position])
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

    override fun onBackPressed() {
        AlertDialog.Builder(this).apply {
            title = "알림"
            setMessage("푸드컵을 종료하시겠습니까?")
            setNegativeButton("취소", null)
            setPositiveButton("종료") {_, _ -> finish() }
            show()
        }
    }


}
