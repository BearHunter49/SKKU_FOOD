package com.example.skku_food.database

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.skku_food.data.res_data
import com.example.skku_food.data.res_menu_data

@Dao
interface RawDAO{
    @RawQuery
    fun getJustNamePhone(query: SupportSQLiteQuery): List<res_data>

    @RawQuery
    fun getFullResInfo(query: SupportSQLiteQuery): List<res_menu_data>

}