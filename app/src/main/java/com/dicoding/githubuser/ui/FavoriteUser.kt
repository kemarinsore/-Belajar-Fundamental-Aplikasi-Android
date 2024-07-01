package com.dicoding.githubuser.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.databinding.FavoriteActivityBinding
import com.dicoding.githubuser.viewModel.FavoriteViewModel
import com.dicoding.githubuser.viewModel.ViewModelFactory

class FavoriteUser : AppCompatActivity() {

    private lateinit var _favoriteActivityBinding: FavoriteActivityBinding
    private lateinit var favViewModel : FavoriteViewModel
    private lateinit var favAdapter : FavoriteAdapter

    @SuppressLint("NotifyDataChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _favoriteActivityBinding = FavoriteActivityBinding.inflate(layoutInflater)
        setContentView(_favoriteActivityBinding.root)

        val favModelFactory = ViewModelFactory(application)
        favViewModel = ViewModelProvider(this, favModelFactory).get(FavoriteViewModel::class.java)

        val rv_fav =_favoriteActivityBinding.rvFavorite
        favAdapter = FavoriteAdapter { favoriteuser ->
            val IntentFav = Intent (this, DetailUser::class.java)
            IntentFav.putExtra("username", favoriteuser.username)
            startActivity(IntentFav)
        }

        rv_fav.apply {
            layoutManager = LinearLayoutManager(context)
            adapter =favAdapter
        }

        favViewModel.favoriteUsers.observe(this){userFav ->
            val Items = arrayListOf<ItemsItem>()
            userFav.map{
                val fav_item = ItemsItem(login = it.username, avatarUrl = it.avatarUrl, id = it.id)
                Items.add(fav_item)
            }
            favAdapter.submitList(userFav)
            favAdapter.notifyDataSetChanged()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home ->{
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



}