package com.tinle.emptyproject.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.tinle.emptyproject.vm.*
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

    @Binds
    @IntoMap
    @ViewModelKey(SettingsVM::class)
    abstract fun bindSettingsVM(viewModel: SettingsVM):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RewardsManagerVM::class)
    abstract fun bindRewardsManagerVM(viewModel: RewardsManagerVM):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpVM::class)
    abstract fun bindSignUpVM(viewModel: SignUpVM):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PaymentVM::class)
    abstract fun bindPaymentVM(viewModel: PaymentVM):ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ManageTransactionVM::class)
    abstract fun bindManageTransVM(viewModel: ManageTransactionVM):ViewModel






}