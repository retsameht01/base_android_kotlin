package com.tinle.emptyproject.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.tinle.emptyproject.vm.CheckinViewModel
import com.tinle.emptyproject.vm.CustomerRewardsVM
import com.tinle.emptyproject.vm.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal  abstract fun bindViewModelFactory(factory: ViewModelFactory):ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(vieModel:MainViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CheckinViewModel::class)
    abstract fun bindsCheckinModel(viewModel: CheckinViewModel):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CustomerRewardsVM::class)
    abstract fun bindsRewardModel(viewModel: CustomerRewardsVM):ViewModel

}