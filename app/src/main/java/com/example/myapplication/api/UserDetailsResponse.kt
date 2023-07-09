package com.example.myapplication.api

import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(
    @SerializedName("login") val login: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("avatar_url") val avatar_url: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("html_url") val html_url: String?,
    @SerializedName("followers_url") val followers_url: String?,
    @SerializedName("following_url") val following_url: String?,
    @SerializedName("starred_url") val starred_url: String?,
    @SerializedName("repos_url") val repos_url: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("company") val company: Any?,
    @SerializedName("blog") val blog: String?,
    @SerializedName("location") val location: String?,
    @SerializedName("email") val email: Any?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("public_repos") val public_repos: Int?,
    @SerializedName("public_gists") val public_gists: Int?,
    @SerializedName("followers") val followers: Int?,
    @SerializedName("following") val following: Int?,
    @SerializedName("created_at") val created_at: String?,
    @SerializedName("updated_at") val updated_at: String?
)