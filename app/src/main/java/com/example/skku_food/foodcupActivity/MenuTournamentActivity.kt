package com.example.skku_food.foodcupActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.skku_food.R
import com.example.skku_food.data.MenuFoodCupData
import com.example.skku_food.data.MenuFoodCupImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tournament.*
import kotlin.random.Random

class MenuTournamentActivity : AppCompatActivity() {

    private lateinit var model: MenuViewModel
    private var startList = MenuFoodCupImage.menuInfo.toMutableList()
    private var endList:MutableList<MenuFoodCupData> = mutableListOf()
    private var totalCount = startList.size / 2
    private var count = 1
    private var round = 1
    private var _position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament)

        //ViewModel Observer 달아주기
        model = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val menuObserver = Observer<List<MenuFoodCupData>>{ newList ->
            Picasso.get().load(newList[0].img).into(imageView1)
            Picasso.get().load(newList[1].img).into(imageView2)
            text_menu1.text = newList[0].name
            text_menu2.text = newList[1].name
        }
        model.currentMenu.observe(this, menuObserver)


        // Initial state
        val firstItem = startList.random()
        startList.remove(firstItem)
        val secondItem = startList.random()
        startList.remove(secondItem)
        model.currentMenu.value = listOf(firstItem, secondItem)
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
                imageView1.isEnabled = true
                imageView2.isEnabled = true
                btn_random.isEnabled = true
            }

            override fun onAnimationStart(animation: Animation?) {
                imageView1.isEnabled = false
                imageView2.isEnabled = false
                btn_random.isEnabled = false
            }

        })


        // --------------------Button Click----------------------------------
        imageView1.setOnClickListener {
            _position = 0
            it.bringToFront()
            it.startAnimation(myAnim)
        }

        imageView2.setOnClickListener {
            _position = 1
            it.bringToFront()
            it.startAnimation(myAnim)
        }

        btn_random.setOnClickListener {
            _position = Random.nextInt(2)
            when(_position){
                0 -> imageView1.apply {
                    bringToFront()
                    startAnimation(myAnim)
                }
                1 -> imageView2.apply {
                    bringToFront()
                    startAnimation(myAnim)
                }
            }
        }
    }


    private fun tournament(position:Int){
        endList.add(model.currentMenu.value!![position])
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
                val intent = Intent(this, MenuFinishActivity::class.java)
                intent.putExtra("menu", endList[0].name)
                intent.putExtra("img", endList[0].img)
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
            model.currentMenu.value = listOf(first, second)
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
