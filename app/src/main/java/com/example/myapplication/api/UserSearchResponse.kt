package com.example.myapplication.api

import com.example.myapplication.model.User
import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("total_count") val total: Int = 0,
    @SerializedName("incomplete_results") val isIncomplete: Boolean = false,
    @SerializedName("items") val items: List<User> = emptyList(),
)