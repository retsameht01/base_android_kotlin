package com.tinle.emptyproject.di

import com.tinle.emptyproject.view.CheckinFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun checkinFrag():CheckinFragment

}