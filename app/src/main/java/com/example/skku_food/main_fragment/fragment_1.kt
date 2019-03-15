package com.example.skku_food.main_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skku_food.MainActivity
import com.example.skku_food.R
import com.example.skku_food.adapter.menu_rcl_adt
import com.example.skku_food.adapter.menu_rcl_itemdecor
import kotlinx.android.synthetic.main.activity_fragment_1.view.*

class fragment_1 : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.activity_fragment_1, container, false)
        rootView.recycler_view.apply {
            adapter = menu_rcl_adt()
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(menu_rcl_itemdecor(3, resources.getDimensionPixelSize(R.dimen.item_offset)))

        }

        return rootView
    }
}