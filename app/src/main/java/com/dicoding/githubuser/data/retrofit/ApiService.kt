package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.response.GitHubResponse
import com.dicoding.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
    fun searchGitHubUser (@Query("q")q: String): Call<GitHubResponse>

    @GET("users/{username}")
    fun getUserDetail (@Path("username")username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers (@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing (@Path("username") username: String): Call<List<ItemsItem>>



}
