package com.dicoding.githubuser.repository

import androidx.lifecycle.LiveData
import com.dicoding.githubuser.database.Favorite
import com.dicoding.githubuser.database.FavoriteDao
import com.dicoding.githubuser.ui.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class FavoriteRepository(
    private val favoriteDao: FavoriteDao,
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
){

    fun getAllFavorite(): LiveData<List<Favorite>> {
        return favoriteDao.getAllFavorites()
    }

    fun insert(user: Favorite, favStatus:Boolean){
        executorService.execute{
            user.isfavorite = favStatus
            favoriteDao.insert(user)
        }
    }

    fun delete(user: Favorite, favStatus: Boolean){
        executorService.execute{
            user.isfavorite = favStatus
            favoriteDao.delete(user)
        }
    }

    fun getfavoriteUsername(username:String): LiveData<Favorite> {
        return favoriteDao.getFavUsername(username)
    }

}