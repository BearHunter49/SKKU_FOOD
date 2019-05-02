package com.example.skku_food.mainFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.skku_food.R
import com.example.skku_food.adapter.MenuRclAdt
import com.example.skku_food.adapter.MenuRclItemDecor
import kotlinx.android.synthetic.main.activity_fragment_1.view.*

class Fragment1 : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.activity_fragment_1, container, false)
        rootView.recycler_view.apply {
            adapter = MenuRclAdt()
            layoutManager = GridLayoutManager(context, 3)
            addItemDecoration(MenuRclItemDecor(3, resources.getDimensionPixelSize(R.dimen.item_offset)))

        }

        return rootView
    }
}