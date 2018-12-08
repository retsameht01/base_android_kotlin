package com.tinle.emptyproject.di

import android.content.Context
import com.tinle.emptyproject.App
import com.tinle.emptyproject.api.ApiHandler
import com.tinle.emptyproject.core.AppExecutor
import com.tinle.emptyproject.core.PreferenceStore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideAppContext(app: App): Context = app

    @Provides
    @Singleton
    fun provideExecutor():AppExecutor{
        return AppExecutor()
    }

    @Provides
    @Singleton
    fun provideApi(pref:PreferenceStore):ApiHandler{
        return ApiHandler(pref)
    }

    @Provides
    @Singleton
    fun providePrefStore(app:App):PreferenceStore{
        return PreferenceStore(app)
    }
}