package com.example.tms_app.DataBase.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tms_app.DataBase.Entities.Tv
import com.example.tms_app.DataBase.Movie
import com.example.tms_app.DataBase.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao{
    @Query("select * from Tv")
    fun getTv(): Flow<List<Tv?>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleTv(tv: Tv)

    @Query("delete from Tv")
    suspend fun emptyTheTable()

    @Query("delete from Tv")
    suspend fun emptyUserInfo()

    @Query("select sessionId from UserInfo where record_id = (select max(record_id) from UserInfo)")
    suspend fun getSessionId(): String

    @Query("update Tv set isFavourite = :isFavourite where id = :tv_id ")
    suspend fun updateFavouriteOfTv(isFavourite: Boolean, tv_id: Int)
}