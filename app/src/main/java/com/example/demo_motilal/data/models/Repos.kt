package com.example.demo_motilal.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Repos(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    val node_id: String?,
    val name: String?,
    val full_name: String?,
    val description: String?,
    val created_at: String?,
    val updated_at: String?,
    val stargazers_count: String?,
    val watchers_count: String?,
    val language: String?
)