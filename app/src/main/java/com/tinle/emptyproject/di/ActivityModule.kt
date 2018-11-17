package com.tinle.emptyproject.di

import com.tinle.emptyproject.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun bindMainActivity():MainActivity
}