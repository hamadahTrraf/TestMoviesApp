package com.example.tms_app.DataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tms_app.DataBase.Dao.MovieDao
import com.example.tms_app.DataBase.Dao.TvDao
import com.example.tms_app.DataBase.Entities.Tv

@Database(entities = [Movie::class,UserInfo::class, Tv::class, MovieDetailsDb::class], version = 59)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvDao(): TvDao


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "DB"
                )
                    .fallbackToDestructiveMigration()

                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}