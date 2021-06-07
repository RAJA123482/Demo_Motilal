package com.example.demo_motilal.data.network

import com.example.demo_motilal.data.models.TrendingRepoResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ReposApi {
    @GET("search/repositories")
    fun getRepos(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String
    ) : Single<Response<TrendingRepoResponse>>

}