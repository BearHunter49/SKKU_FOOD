package com.example.skku_food.foodcupActivity

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.skku_food.data.ResSimpleData

class ResViewModel: ViewModel(){
    val currentRes: MutableLiveData<List<ResSimpleData>> by lazy {
        MutableLiveData<List<ResSimpleData>>()
    }
}