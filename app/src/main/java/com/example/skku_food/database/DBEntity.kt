package com.example.skku_food.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chicken")
data class ChickenEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                         val longitude: String, val phone: String)

@Entity(tableName = "chinese")
data class ChineseEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                         val longitude: String, val phone: String)

@Entity(tableName = "curry")
data class CurryEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                       val longitude: String, val phone: String)

@Entity(tableName = "cutlet")
data class CutletEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                        val longitude: String, val phone: String)

@Entity(tableName = "hamburger")
data class HamburgerEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                           val longitude: String, val phone: String)

@Entity(tableName = "instant")
data class InstantEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                         val longitude: String, val phone: String)

@Entity(tableName = "korean")
data class KoreanEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                        val longitude: String, val phone: String)

@Entity(tableName = "lunchbox")
data class LunchboxEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                          val longitude: String, val phone: String)

@Entity(tableName = "meat")
data class MeatEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                      val longitude: String, val phone: String)

@Entity(tableName = "noodle")
data class NoodleEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                        val longitude: String, val phone: String)

@Entity(tableName = "pizza")
data class PizzaEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                       val longitude: String, val phone: String)

@Entity(tableName = "restaurant")
data class RestaurantEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                            val longitude: String, val phone: String)

@Entity(tableName = "soup")
data class SoupEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                      val longitude: String, val phone: String)

@Entity(tableName = "sushi")
data class SushiEntity(@PrimaryKey val name: String, val menu: String, val latitude: String,
                       val longitude: String, val phone: String)