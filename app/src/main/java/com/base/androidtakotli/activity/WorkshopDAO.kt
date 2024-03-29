package com.base.androidtakotli.activity

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WorkshopDAO {
    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(workshop: Workshop)

    @Query("SELECT * FROM workshop_table")
    fun getAll(): List<Workshop>

    @Query("DELETE FROM workshop_table")
    fun deleteAll()
}