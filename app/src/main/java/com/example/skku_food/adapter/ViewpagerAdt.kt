package com.example.skku_food.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.skku_food.mainFragment.Fragment1
import com.example.skku_food.mainFragment.Fragment2
import com.example.skku_food.mainFragment.Fragment3

class ViewpagerAdt(fm: androidx.fragment.app.FragmentManager) : FragmentStatePagerAdapter(fm){

    private val titleList = listOf("메뉴", "문의", "푸드컵")

    override fun getItem(position: Int): Fragment {
        return when(position){
            0   -> Fragment1()
            1   -> Fragment2()
            2   -> Fragment3()
            else -> Fragment1()
        }
    }

    override fun getCount(): Int = titleList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

}