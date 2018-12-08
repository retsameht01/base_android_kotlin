package com.tinle.emptyproject.di

import com.tinle.emptyproject.view.CheckinFragment
import com.tinle.emptyproject.view.RewardsFragment
import com.tinle.emptyproject.view.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun checkinFrag():CheckinFragment

    @ContributesAndroidInjector
    abstract fun settingsFrag():SettingsFragment

    @ContributesAndroidInjector
    abstract fun rewardsFrag():RewardsFragment

}