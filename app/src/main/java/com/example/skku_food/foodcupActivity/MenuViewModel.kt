package com.example.skku_food.foodcupActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skku_food.data.MenuFoodCupData

class MenuViewModel: ViewModel(){
    val currentMenu: MutableLiveData<List<MenuFoodCupData>> by lazy {
        MutableLiveData<List<MenuFoodCupData>>()
    }
}