package com.example.skku_food.database

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.example.skku_food.data.ResSimpleData
import com.example.skku_food.data.ResFullData

@Dao
interface RawDAO{
    @RawQuery
    fun getJustNamePhone(query: SupportSQLiteQuery): List<ResSimpleData>

    @RawQuery
    fun getFullResInfo(query: SupportSQLiteQuery): ResFullData

}