package com.dicoding.githubuser.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "UserFavorite")
@Parcelize
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int? = 0,

    @ColumnInfo(name = "username")
    var username: String?,

    @ColumnInfo(name = "profil")
    var avatarUrl: String?,

    @ColumnInfo(name = "favorite")
    var isfavorite: Boolean

) : Parcelable
