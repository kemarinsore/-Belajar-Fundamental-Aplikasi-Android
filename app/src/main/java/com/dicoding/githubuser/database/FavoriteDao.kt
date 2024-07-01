package com.dicoding.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dicoding.githubuser.ui.FavoriteUser

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("SELECT * from userfavorite ORDER BY username ASC")
    fun getAllFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM userfavorite WHERE username = :username AND favorite = 1")
    fun getFavUsername(username: String): LiveData<Favorite>
}