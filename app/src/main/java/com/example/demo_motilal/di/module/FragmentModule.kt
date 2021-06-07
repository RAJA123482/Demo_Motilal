package com.example.demo_motilal.di.module

import androidx.fragment.app.Fragment
import com.example.demo_motilal.AppConstants
import com.example.demo_motilal.data.network.ReposApi
import com.example.demo_motilal.ui.adapter.TrendingReposAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@Module
class FragmentModule(val fragment: Fragment) {

    @Provides
    fun provideApiList(): ReposApi {
        var okHttpClient = OkHttpClient.Builder()
            .connectTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)
            .readTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)

        val retrofit = Retrofit.Builder().client(okHttpClient.build()).baseUrl(AppConstants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

        return retrofit.create(ReposApi::class.java)
    }

    @Provides
    fun provideTrendingReposAdapter() : TrendingReposAdapter {
        return TrendingReposAdapter(fragment = fragment, arrayList = ArrayList())
    }


}