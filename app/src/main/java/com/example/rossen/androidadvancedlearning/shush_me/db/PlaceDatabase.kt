package com.example.rossen.androidadvancedlearning.shush_me.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

@Database(entities = arrayOf(Place::class), version = 1)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {

        @Volatile private var INSTANCE: PlaceDatabase? = null

        fun getInstance(context: Context): PlaceDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        PlaceDatabase::class.java, "Sample.db")
                        .build()
    }
}