package com.base.androidtakotli.activity

import android.content.Context
import androidx.room.Room

class WorkshopRepository private constructor(mCtx: Context) {
    //our app database object
    private val appDatabase: WorkshopRoomDB =
        Room.databaseBuilder(mCtx, WorkshopRoomDB::class.java, "all_data").build()

    fun getAppDatabase(): WorkshopRoomDB {
        return appDatabase
    }

    companion object {
        private var mInstance: WorkshopRepository? = null
        @Synchronized
        fun getInstance(mCtx: Context): WorkshopRepository? {
            if (mInstance == null) {
                mInstance = WorkshopRepository(mCtx)
            }
            return mInstance
        }
    }

}