package com.tinle.emptyproject.di

import android.arch.persistence.room.Room
import android.content.Context
import com.tinle.emptyproject.App
import com.tinle.emptyproject.api.GposService
import com.tinle.emptyproject.core.*
import com.tinle.emptyproject.data.AppDatabase
import com.tinle.emptyproject.data.CheckinDao
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
    fun provideApi(pref:PreferenceStore, encryptService:EncryptionService, dateUtil:DateUtil):GposService{
        return GposService(pref, encryptService, dateUtil)
    }

    @Provides
    @Singleton
    fun providePrefStore(app:App):PreferenceStore{
        return PreferenceStore(app)
    }

    @Provides
    @Singleton
    fun provideDb(app:App):AppDatabase{
        val db = Room.databaseBuilder(
                app,
                AppDatabase::class.java, "gpos_rewards"
        ).build()
        return db
    }

    @Provides
    fun provideCheckinDao(db: AppDatabase):CheckinDao{
        return db.checkinDao()
    }

    @Provides
    @Singleton
    fun provideDateUtil():DateUtil{
        return DateUtil()
    }

    @Provides
    @Singleton
    fun provideEncryptServide(): EncryptionService {
        return EncryptionService()
    }

}