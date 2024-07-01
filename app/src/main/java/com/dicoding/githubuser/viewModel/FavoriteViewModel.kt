package com.dicoding.githubuser.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.database.Favorite
import com.dicoding.githubuser.database.FavoriteRoomDatabase
import com.dicoding.githubuser.repository.FavoriteRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteRepository: FavoriteRepository =
        FavoriteRepository(FavoriteRoomDatabase.getDatabase(application).favoriteDao())

    var favoriteUsers :LiveData<List<Favorite>> = favoriteRepository.getAllFavorite()

    fun insert(favorite: Favorite) {
        favoriteRepository.insert(favorite, true)
    }

    fun delete(favorite: Favorite) {
        favoriteRepository.delete(favorite,true)
    }

    fun getFavoriteUsername(username: String): LiveData<Favorite> {
        return favoriteRepository.getfavoriteUsername(username)
    }
}