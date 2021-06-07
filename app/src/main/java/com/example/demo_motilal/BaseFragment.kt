package com.example.demo_motilal

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.demo_motilal.di.component.DaggerFragmentComponent
import com.example.demo_motilal.di.component.FragmentComponent
import com.example.demo_motilal.di.module.FragmentModule

open class BaseFragment : Fragment() {
    lateinit var fragmentComponent: FragmentComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentComponent = DaggerFragmentComponent.builder().fragmentModule(FragmentModule(this)).applicationComponent(((activity?.applicationContext) as RepoApplication).applicationComponent).build()
    }
}