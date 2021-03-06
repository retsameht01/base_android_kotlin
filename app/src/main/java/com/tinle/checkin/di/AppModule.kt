package com.tinle.checkin.di

import android.arch.persistence.room.Room
import android.content.Context
import com.tinle.checkin.App
import com.tinle.checkin.api.GposService
import com.tinle.checkin.core.*
import com.tinle.checkin.data.AppDatabase
import com.tinle.checkin.data.CheckinDao
import com.tinle.checkin.data.CustomerDao
import com.tinle.checkin.data.PaymentTransactionDao
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
        ).fallbackToDestructiveMigration()
                .build()
        return db
    }

    @Provides
    fun provideCheckinDao(db: AppDatabase):CheckinDao{
        return db.checkinDao()
    }

    @Provides
    fun provideTransactionDao(db:AppDatabase):PaymentTransactionDao{
        return db.paymentTransactionDao()
    }

    @Provides
    fun provideCustomerDao(db:AppDatabase):CustomerDao{
        return db.customerDao()
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