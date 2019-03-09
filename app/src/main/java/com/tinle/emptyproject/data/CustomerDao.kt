package com.tinle.emptyproject.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg customer:RewardsMember)

    @Query("SELECT * FROM RewardsMember")
    fun getAllCustomers():List<RewardsMember>


}