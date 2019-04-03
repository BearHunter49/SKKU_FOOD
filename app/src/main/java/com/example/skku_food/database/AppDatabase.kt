package com.example.skku_food.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [KoreanEntity::class, ChineseEntity::class, CutletEntity::class, ChickenEntity::class,
    PizzaEntity::class, InstantEntity::class, HamburgerEntity::class, LunchboxEntity::class, SoupEntity::class,
    NoodleEntity::class, SushiEntity::class, MeatEntity::class, RestaurantEntity::class, CurryEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase(){

    abstract fun rawDAO(): RawDAO

    companion object {
        @JvmField
        val MIGRATION_1_2 : Migration = object : Migration(1, 2){
            override fun migrate(database: SupportSQLiteDatabase) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        }
    }

}