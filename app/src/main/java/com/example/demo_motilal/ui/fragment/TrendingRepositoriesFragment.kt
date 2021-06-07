package com.example.demo_motilal.ui.fragment

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo_motilal.BaseFragment
import com.example.demo_motilal.R
import com.example.demo_motilal.data.models.Repos
import com.example.demo_motilal.service.RepoSyncService
import com.example.demo_motilal.ui.adapter.TrendingReposAdapter
import com.example.demo_motilal.ui.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.fragment_trending_repositories.*
import javax.inject.Inject

class TrendingRepositoriesFragment : BaseFragment(), TrendingReposAdapter.ReposCallback {
    @Inject
    lateinit var reposViewModel: ReposViewModel

    @Inject
    lateinit var trendingReposAdapter: TrendingReposAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentComponent.inject(this)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending_repositories, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rcv_repos.apply {
            layoutManager = LinearLayoutManager(this@TrendingRepositoriesFragment.activity)
            adapter = trendingReposAdapter
        }

        if(!isServiceRunning(requireContext(), RepoSyncService::class.java)) {
            val serviceIntent = Intent(this@TrendingRepositoriesFragment.context, RepoSyncService::class.java)
            requireContext().startService(serviceIntent)
        }

        srl_repos.isRefreshing = true

        srl_repos.setOnRefreshListener {
            performNetworkCall()
        }

        reposViewModel.getReposCount(requireContext())
        reposViewModel.reposCountFromDb?.observe(viewLifecycleOwner, Observer {
            if(it <= 0) {
                println("no records found")
                performNetworkCall()
            } else {
                println("$it records found")

                reposViewModel.getReposFromDb((context)!!)
                reposViewModel.trendingReposFromDb?.observe(viewLifecycleOwner, Observer {
                    if(it.isNotEmpty()) {
                        rcv_repos.visibility = View.VISIBLE
                        tv_empty.visibility = View.GONE
                        trendingReposAdapter.addItems((it as? ArrayList<Repos>)!!)
                    } else {
                        rcv_repos.visibility = View.GONE
                        tv_empty.visibility = View.VISIBLE
                    }

                    if(srl_repos.isRefreshing) {
                        srl_repos.isRefreshing = false
                    }
                })

            }
        })

    }

    private fun isServiceRunning(context: Context, serviceClass: Class<*>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        for (service in manager!!.getRunningServices(Int.MAX_VALUE)) {
            if (serviceClass.name == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun performNetworkCall() {
        val connectivityManage = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManage.activeNetworkInfo
        networkInfo?.let {
            if(it.isConnected) {
                reposViewModel.getRepos()
                reposViewModel.trendingReposList?.observe(viewLifecycleOwner, Observer {
                    if(it?.items?.size!! > 0) {

                        saveReposInDb(it?.items)

                        rcv_repos.visibility = View.VISIBLE
                        tv_empty.visibility = View.GONE
                        trendingReposAdapter.addItems(it?.items)
                    } else {
                        rcv_repos.visibility = View.GONE
                        tv_empty.visibility = View.VISIBLE
                    }
                })
            } else {
                Toast.makeText(context, "Please check internet connection...", Toast.LENGTH_LONG).show()
            }
        } ?: Toast.makeText(context, "No network available...", Toast.LENGTH_LONG).show()


    }

    private fun saveReposInDb(items: ArrayList<Repos>) {
            context?.let {
                reposViewModel.addReposInDb(it, items)
                reposViewModel.addReposInDb?.observe(viewLifecycleOwner, Observer {
                    if(it) {
                        Toast.makeText(requireContext(), "Data inserted in local db successfully...", Toast.LENGTH_SHORT).show()
                    }
                    if(srl_repos.isRefreshing) {
                        srl_repos.isRefreshing = false
                    }
                })
            }
    }

    override fun repoClicked(id: Int) {
        val action = TrendingRepositoriesFragmentDirections.gotoDetails()
            .setId(id)
        findNavController().navigate(action)
    }

}