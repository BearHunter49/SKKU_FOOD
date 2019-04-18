package com.example.skku_food.foodcupActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.skku_food.R
import com.example.skku_food.data.foodmenu
import com.example.skku_food.data.menuImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_tournament.*

class TournamentActivity : AppCompatActivity() {

    private lateinit var model: MenuViewModel
    private var startList = menuImage.menuInfo.toMutableList()
    private var endList:MutableList<foodmenu> = mutableListOf()
    private var totalCount = startList.size / 2
    private var count = 1
    private var round = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tournament)


        //ViewModel Observer 달아주기
        model = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        val menuObserver = Observer<List<foodmenu>>{ newList ->
            Picasso.get().load(newList[0].url).into(imageView1)
            Picasso.get().load(newList[1].url).into(imageView2)
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


        // --------------------Button Click----------------------------------
        imageView1.setOnClickListener {
            endList.add(model.currentMenu.value!![0])
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
                    intent.putExtra("url", endList[0].url)
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

        imageView2.setOnClickListener {
            endList.add(model.currentMenu.value!![1])
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
                    intent.putExtra("url", endList[0].url)
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
    }


}
