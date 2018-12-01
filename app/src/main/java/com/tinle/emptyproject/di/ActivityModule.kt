package com.tinle.emptyproject.di

import com.tinle.emptyproject.MainActivity
import com.tinle.emptyproject.view.CheckInActivity
import com.tinle.emptyproject.view.CustomerRewardsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity():MainActivity

    @ContributesAndroidInjector
    abstract fun bindCheckinActivity():CheckInActivity

    @ContributesAndroidInjector
    abstract fun bindRewardsActivity():CustomerRewardsActivity
}