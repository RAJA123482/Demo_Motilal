package com.example.demo_motilal

import android.app.Application
import com.example.demo_motilal.di.component.ApplicationComponent
import com.example.demo_motilal.di.component.DaggerApplicationComponent
import com.example.demo_motilal.di.module.ApplicationModule

class RepoApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent
    override fun onCreate() {
        super.onCreate()
        //setup dagger
        applicationComponent = DaggerApplicationComponent.builder().applicationModule(
            ApplicationModule(this)
        ).build()
    }
}