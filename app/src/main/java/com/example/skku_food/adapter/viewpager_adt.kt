package com.example.skku_food.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.skku_food.mainFragment.fragment_1
import com.example.skku_food.mainFragment.fragment_2
import com.example.skku_food.mainFragment.fragment_3

class viewpager_adt(fm: androidx.fragment.app.FragmentManager) : FragmentStatePagerAdapter(fm){

    private val titleList = listOf("메뉴", "추천", "푸드컵")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0   -> fragment_1()
            1   -> fragment_2()
            2   -> fragment_3()
            else -> fragment_1()
        }
    }

    override fun getCount(): Int = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}