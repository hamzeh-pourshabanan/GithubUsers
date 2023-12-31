package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(
    @field:SerializedName("login") val login: String,
    @PrimaryKey @field:SerializedName("id") val id: Long,
    @field:SerializedName("node_id") val node_id: String?,
    @field:SerializedName("avatar_url") val avatar_url: String?,
    @field:SerializedName("gravatar_id") val gravatar_id: String?,
    @field:SerializedName("url") val url: String?,
    @field:SerializedName("html_url") val html_url: String?,
    @field:SerializedName("followers_url") val followers_url: String?,
    @field:SerializedName("subscriptions_url") val subscriptions_url: String?,
    @field:SerializedName("organizations_url") val organizations_url: String?,
    @field:SerializedName("repos_url") val repos_url: String?,
    @field:SerializedName("received_events_url") val received_events_url: String?,
    @field:SerializedName("type") val type: String?,
    @field:SerializedName("score") val score: Int?,
    @field:SerializedName("following_url") val following_url: String?,
    @field:SerializedName("gists_url") val gists_url: String?,
    @field:SerializedName("starred_url") val starred_url: String?,
    @field:SerializedName("events_url") val events_url: String?,
    @field:SerializedName("site_admin") val site_admin: Boolean?
)