package com.example.demo_motilal.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.demo_motilal.data.models.Repos
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
import retrofit2.Response
import javax.inject.Inject

class TrendingReposRepository @Inject constructor(private val reposApi: ReposApi) {

    fun getRepos(q: String, sort: String, order: String) : LiveData<TrendingRepoResponse> {
        val mutableData = MutableLiveData<TrendingRepoResponse>()
        reposApi.getRepos(q, sort, order)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Response<TrendingRepoResponse>>{
                override fun onSuccess(t: Response<TrendingRepoResponse>) {
                    mutableData.value = t.body()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }

            })

        return mutableData
    }

    fun addReposToDb(context: Context, list: ArrayList<Repos>): LiveData<Boolean> {
        val mutableData = MutableLiveData<Boolean>()

        val reposDao = ReposDatabase(context).getReposDao()

        CoroutineScope(Dispatchers.IO).launch {
            val list = reposDao.addReposList(list)
            withContext(Dispatchers.Main) {
                mutableData.value = true
            }
        }

        return mutableData
    }

    fun getRepoListFromDb(context: Context) : LiveData<List<Repos>> {
        val mutableData = MutableLiveData<List<Repos>>()
        val reposDao = ReposDatabase(context).getReposDao()
        CoroutineScope(Dispatchers.IO).launch {
            val list = reposDao.getAllRepositories()
            withContext(Dispatchers.Main) {
                mutableData.value = list as ArrayList<Repos>
            }
        }
        return mutableData
    }

    fun getSingleRepoFromDb(context: Context, id: Int) : LiveData<Repos> {
        val mutableData = MutableLiveData<Repos>()

        val reposDao = ReposDatabase(context).getReposDao()
        CoroutineScope(Dispatchers.IO).launch {
            val reposModel = reposDao.getRepository(id)
            withContext(Dispatchers.Main) {
                mutableData.value = reposModel
            }
        }
        return mutableData
    }

    fun getReposCount(context: Context): LiveData<Int> {
        val mutableData = MutableLiveData<Int>()

        val reposDao = ReposDatabase(context).getReposDao()
        CoroutineScope(Dispatchers.IO).launch {
            val list = reposDao.getReposCount()
            withContext(Dispatchers.Main) {
                mutableData.value = list
            }
        }
        return mutableData
    }

}