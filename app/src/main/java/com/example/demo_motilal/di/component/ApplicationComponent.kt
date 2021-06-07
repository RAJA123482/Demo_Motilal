package com.example.demo_motilal.di.component

import android.content.Context
import com.example.demo_motilal.di.module.ApplicationModule
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun provideApplicationContext(): Context
    fun provideCompoiteDisposable(): CompositeDisposable
}