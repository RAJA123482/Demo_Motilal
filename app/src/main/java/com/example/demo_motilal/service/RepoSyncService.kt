package com.example.demo_motilal.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import com.example.demo_motilal.AppConstants
import com.example.demo_motilal.data.models.TrendingRepoResponse
import com.example.demo_motilal.data.network.ReposApi
import com.example.demo_motilal.db.ReposDatabase
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class RepoSyncService : Service() {

    lateinit var reposApi: ReposApi
    lateinit var retrofit: Retrofit
    lateinit var okHttpClient: OkHttpClient.Builder
    lateinit var handler: Handler
    lateinit var timer : Timer
    lateinit var timerTask: TimerTask

    override fun onCreate() {
        super.onCreate()
        println("Service started...")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)
            .readTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout((1000 * 60).toLong(), TimeUnit.MILLISECONDS)
        retrofit = Retrofit.Builder().client(okHttpClient.build()).baseUrl(AppConstants.BASE_URL).addConverterFactory(
            GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
        reposApi =  retrofit.create(ReposApi::class.java)

        handler = Handler()
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    try {
                        fetchReposFromServer()
                    } catch (e: Exception) {
                    }
                }
            }
        }
        timer.schedule(timerTask, 0, 15 * 60000)
        return  START_STICKY
    }

    private fun fetchReposFromServer() {
        reposApi.getRepos("language", "trending", "desc")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<TrendingRepoResponse>> {
                override fun onSuccess(t: Response<TrendingRepoResponse>) {
                    CoroutineScope(Dispatchers.IO).launch {
                        applicationContext?.let {
                            val reposDao = ReposDatabase(it).getReposDao()
                            reposDao.addReposList(t.body()?.items!!)
                            withContext(Dispatchers.Main) {
                                println("Data inserted in db...")
                            }
                        }
                    }
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            })

    }

    override fun onDestroy() {
        super.onDestroy()
        println("Service Destroyed")
        timer.cancel()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}