package com.example.demo_motilal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.demo_motilal.BaseFragment
import com.example.demo_motilal.R
import com.example.demo_motilal.data.models.Repos
import com.example.demo_motilal.getFormattedDate
import com.example.demo_motilal.ui.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.fragment_repository_details.*
import javax.inject.Inject

class RepositoryDetailsFragment : BaseFragment() {
    @Inject
    lateinit var reposViewModel: ReposViewModel

    var id: Int? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentComponent.inject(this)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repository_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            this.id = RepositoryDetailsFragmentArgs.fromBundle(
                it
            ).id
        }

        srl_details.isRefreshing = true

        srl_details.setOnRefreshListener {
            if(srl_details.isRefreshing) {
                srl_details.isRefreshing = false
            }
        }

        context?.let {
            reposViewModel.getSingleRepo(it, id!!)
            reposViewModel.singleRepoFromDb?.observe(viewLifecycleOwner, Observer {
                setupUI(it)

                if(srl_details.isRefreshing) {
                    srl_details.isRefreshing = false
                }
            })
        }

    }

    private fun setupUI(model: Repos) {
        tv_name.text = "Name: ${model.name}"
        tv_full_name.text = "Full name: ${model.full_name}"
        tv_lamguage.text = "Language used: ${model.language}"
        tv_description.text = "Desription: ${model.description}"
        tv_star_count.text = "Startgazers count: ${model.stargazers_count}"
        tv_watcher_count.text = "Watchers count: ${model.watchers_count}"
        tv_create_date.text = "Created on: ${model.created_at?.getFormattedDate()}"
        tv_update_date.text = "Updated on: ${model.updated_at?.getFormattedDate()}"
    }
}