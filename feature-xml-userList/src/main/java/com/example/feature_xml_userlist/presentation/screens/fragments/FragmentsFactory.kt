package com.example.feature_xml_userlist.presentation.screens.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.example.feature_xml_userlist.di.FeatureContainer

class FragmentsFactory(
    private val featureContainer: FeatureContainer
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            FragmentA::class.java.name -> FragmentA(featureContainer.provideSharedViewModelFactory())
            FragmentB::class.java.name -> FragmentB(featureContainer.provideSharedViewModelFactory())
            else -> super.instantiate(classLoader, className)
        }
    }
}