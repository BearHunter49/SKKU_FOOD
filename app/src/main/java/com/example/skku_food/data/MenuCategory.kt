package com.example.skku_food.data

import com.example.skku_food.R

data class MenuCategoryData(val menu_img:Int, val menu_nm:String)

object MenuCategoryURL{
    val items: List<MenuCategoryData> = listOf(MenuCategoryData(R.drawable.ic_menu_school, "학식"),
        MenuCategoryData(R.drawable.ic_menu_rice, "한식"), MenuCategoryData(R.drawable.ic_menu_chine, "중식"),
        MenuCategoryData(R.drawable.ic_menu_cutlet, "돈까스"), MenuCategoryData(R.drawable.ic_menu_chicken, "치킨"),
        MenuCategoryData(R.drawable.ic_menu_pizza, "피자"), MenuCategoryData(R.drawable.ic_menu_dduck, "분식"),
        MenuCategoryData(R.drawable.ic_menu_burger, "햄버거"), MenuCategoryData(R.drawable.ic_menu_doshi, "도시락"),
        MenuCategoryData(R.drawable.ic_menu_soup, "국밥"), MenuCategoryData(R.drawable.ic_menu_noodle, "면류"),
        MenuCategoryData(R.drawable.ic_menu_sushi, "초밥"), MenuCategoryData(R.drawable.ic_menu_gogi, "고기"),
        MenuCategoryData(R.drawable.ic_menu_restau, "레스토랑"), MenuCategoryData(R.drawable.ic_menu_curry, "카레"))
}