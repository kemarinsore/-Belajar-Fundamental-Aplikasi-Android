package com.dicoding.githubuser.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FolloViewModel: ViewModel() {
    private val dataFollo = MutableLiveData<List<ItemsItem>>()
            val _dataFollo: LiveData<List<ItemsItem>> = dataFollo

    private val showLoading = MutableLiveData<Boolean>()
            val isLoading: LiveData<Boolean> = showLoading


    companion object {
        private const val TAG = "Data Follo ViewModel"
    }

    fun getFollowers(username : String) {
        showLoading.value = true
        val client = ApiConfig.getApiConfig().getFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                showLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        dataFollo.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getFollowing(username: String ="") {
        showLoading.value = true
        val client = ApiConfig.getApiConfig().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                showLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        dataFollo.value = response.body()
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                showLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}
