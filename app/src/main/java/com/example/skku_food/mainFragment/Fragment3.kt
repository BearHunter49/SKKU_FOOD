package com.example.skku_food.mainFragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.skku_food.R
import com.example.skku_food.foodcupActivity.MenuTournamentActivity
import kotlinx.android.synthetic.main.activity_fragment_3.view.*

class Fragment3 : Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.activity_fragment_3, container, false)

        rootview.btn_start.setOnClickListener {
            val intent = Intent(context, MenuTournamentActivity::class.java)
            startActivity(intent)
        }


        return rootview
    }
}