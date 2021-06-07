package com.example.demo_motilal.di.component

import com.example.demo_motilal.di.PerActivity
import com.example.demo_motilal.di.module.FragmentModule
import com.example.demo_motilal.ui.fragment.RepositoryDetailsFragment
import com.example.demo_motilal.ui.fragment.TrendingRepositoriesFragment
import dagger.Component

@PerActivity
@Component(modules = [FragmentModule::class], dependencies = [ApplicationComponent::class])
interface FragmentComponent {
    fun inject(trendingRepositoriesFragment: TrendingRepositoriesFragment)
    fun inject(repositoryDetailsFragment: RepositoryDetailsFragment)
}