package com.example.myapplication.db

import androidx.room.TypeConverter
import com.example.myapplication.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class TypeAdapters {
    @TypeConverter
    fun fromString(value: String?): ArrayList<User?>? {
        val listType: Type = object : TypeToken<ArrayList<User?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<User?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}