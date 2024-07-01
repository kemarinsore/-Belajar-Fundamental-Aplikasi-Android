package com.dicoding.githubuser.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val dataDetailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = dataDetailUser

    private val showLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = showLoading

    companion object {
        private const val TAG = "username"
    }

    init {
        getUserData()
    }

    fun getUserData(username: String ="") {
        showLoading.value = true
        val client = ApiConfig.getApiConfig().getUserDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        dataDetailUser.value = response.body()
                        Log.d(TAG, "onResponse: ")
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                showLoading.value = false
                Log.e("TAG", "onFailure: ${t.message}")
            }
        })
    }
}