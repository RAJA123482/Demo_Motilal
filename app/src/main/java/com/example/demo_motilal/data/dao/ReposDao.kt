package com.example.demo_motilal.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.demo_motilal.data.models.Repos

@Dao
interface ReposDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRepo(repo: Repos)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReposList(repolist: ArrayList<Repos>)

    @Query("SELECT * FROM repos")
    suspend fun getAllRepositories() : List<Repos>

    @Query("SELECT * FROM repos WHERE id = :id")
    suspend fun getRepository(id: Int?): Repos

    @Query("SELECT COUNT(*) FROM repos")
    suspend fun getReposCount() : Int

}