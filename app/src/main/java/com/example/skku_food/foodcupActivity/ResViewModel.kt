package com.example.skku_food.foodcupActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skku_food.data.res_data

class ResViewModel: ViewModel(){
    val currentRes: MutableLiveData<List<res_data>> by lazy {
        MutableLiveData<List<res_data>>()
    }
}