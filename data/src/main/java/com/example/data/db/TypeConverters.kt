package com.example.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson

class TypeConverters {
    @TypeConverter
    fun listToJson(value: List<Int>): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String): List<Int> {
        val objects = Gson().fromJson(value, Array<Int>::class.java) as Array<Int>
        return objects.toList()
    }
}