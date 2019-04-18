package com.example.skku_food.foodcupActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skku_food.data.foodmenu

class MenuViewModel: ViewModel(){
    val currentMenu: MutableLiveData<List<foodmenu>> by lazy {
        MutableLiveData<List<foodmenu>>()
    }
}