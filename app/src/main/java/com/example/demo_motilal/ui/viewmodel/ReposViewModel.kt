package com.example.demo_motilal.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.demo_motilal.data.models.Repos
import com.example.demo_motilal.data.models.TrendingRepoResponse
import com.example.demo_motilal.data.repositories.TrendingReposRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReposViewModel @Inject constructor( private val repoInstance: TrendingReposRepository) : ViewModel() {

    var trendingReposList: LiveData<TrendingRepoResponse>? = null
    var trendingReposFromDb: LiveData<List<Repos>>? = null
    var singleRepoFromDb: LiveData<Repos>? = null
    var reposCountFromDb: LiveData<Int>? = null
    var addReposInDb: LiveData<Boolean>? = null


    fun getRepos() {
        trendingReposList = repoInstance.getRepos("language", "trending", "desc")
    }

    fun addReposInDb(context: Context, list: ArrayList<Repos>) {
        viewModelScope.launch {
            addReposInDb = repoInstance.addReposToDb(context, list)
        }
    }

    fun getSingleRepo(context: Context, id: Int) {
        viewModelScope.launch {
            singleRepoFromDb = repoInstance.getSingleRepoFromDb(context, id)
        }
    }

    fun getReposFromDb(context: Context) {
        viewModelScope.launch {
            trendingReposFromDb = repoInstance.getRepoListFromDb(context)
        }
    }

    fun getReposCount(context: Context) {
        viewModelScope.launch {
            reposCountFromDb = repoInstance.getReposCount(context)
        }
    }
}