package com.example.demo_motilal.di.component

import com.example.demo_motilal.di.PerActivity
import com.example.demo_motilal.ui.MainActivity
import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class])
interface ActivityComponent {
    fun inject(mainActivity: MainActivity)
}