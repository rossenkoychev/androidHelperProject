package com.example.rossen.androidadvancedlearning.shush_me.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface PlaceDao {

    @Query("Select * FROM place WHERE placeid=:id")
    fun getPlaceById(id: String): Flowable<Place>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(place: Place)

    @Query("DELETE FROM place")
    fun deleteAllPlaces()
}