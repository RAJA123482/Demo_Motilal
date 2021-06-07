package com.example.demo_motilal.di.module

import android.app.Application
import android.content.Context
import com.example.demo_motilal.AppConstants
import com.example.demo_motilal.data.network.ReposApi
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class ApplicationModule(var application: Application) {
    @Provides
    fun provideApplicationContext(): Context = application

    @Provides
    fun provideDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }
}
