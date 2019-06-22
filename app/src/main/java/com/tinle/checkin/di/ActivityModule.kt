package com.tinle.checkin.di

import com.tinle.checkin.MainActivity
import com.tinle.checkin.view.CheckInActivity
import com.tinle.checkin.view.CustomerRewardsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun bindMainActivity():MainActivity

    @ContributesAndroidInjector
    abstract fun bindCheckinActivity():CheckInActivity

    @ContributesAndroidInjector
    abstract fun bindRewardsActivity():CustomerRewardsActivity
}