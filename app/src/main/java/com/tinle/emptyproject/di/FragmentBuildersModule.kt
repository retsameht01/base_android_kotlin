package com.tinle.emptyproject.di

import com.tinle.emptyproject.view.*
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

    @ContributesAndroidInjector
    abstract  fun countDownFrag():CountDownFragment

    @ContributesAndroidInjector
    abstract  fun manageRewards():ManageRewardsFragment

    @ContributesAndroidInjector
    abstract  fun sigup():SignUpFragment

}