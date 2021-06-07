package com.example.demo_motilal.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demo_motilal.AppConstants
import com.example.demo_motilal.data.dao.ReposDao
import com.example.demo_motilal.data.models.Repos

@Database(
    entities = [Repos::class],
    version = 1
)
abstract class ReposDatabase : RoomDatabase() {

    abstract fun getReposDao() : ReposDao

    companion object{
        @Volatile var instance : ReposDatabase? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            ReposDatabase::class.java,
            AppConstants.DB_NAME
        ).build()
    }
}