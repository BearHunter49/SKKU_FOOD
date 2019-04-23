package com.example.skku_food.foodcupActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skku_food.data.menu_foodcupData

class MenuViewModel: ViewModel(){
    val currentMenu: MutableLiveData<List<menu_foodcupData>> by lazy {
        MutableLiveData<List<menu_foodcupData>>()
    }
}