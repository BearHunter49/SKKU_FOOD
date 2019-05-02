package com.example.skku_food.data

import com.example.skku_food.R

data class MenuFoodCupData(val url: Int, val name: String)

object MenuFoodCupURL{
    val menuInfo = listOf(MenuFoodCupData(R.drawable.img_chicken, "치킨"),
        MenuFoodCupData(R.drawable.img_pizza, "피자"),
        MenuFoodCupData(R.drawable.img_curry, "카레"),
        MenuFoodCupData(R.drawable.img_sushi, "초밥"),
        MenuFoodCupData(R.drawable.img_noodle, "면류"),
        MenuFoodCupData(R.drawable.img_haksik, "학식"),
        MenuFoodCupData(R.drawable.img_korean, "한식"),
        MenuFoodCupData(R.drawable.img_chinese, "중식"),
        MenuFoodCupData(R.drawable.img_cutlet, "돈까스"),
        MenuFoodCupData(R.drawable.img_instant, "분식"),
        MenuFoodCupData(R.drawable.img_hamburger, "햄버거"),
        MenuFoodCupData(R.drawable.img_lunchbox, "도시락"),
        MenuFoodCupData(R.drawable.img_soup, "국밥"),
        MenuFoodCupData(R.drawable.img_meat, "고기"),
        MenuFoodCupData(R.drawable.img_restaurant, "레스토랑")
    )
}