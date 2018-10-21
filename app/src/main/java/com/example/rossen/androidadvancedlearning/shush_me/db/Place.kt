package com.example.rossen.androidadvancedlearning.shush_me.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName="place")
class Place(@PrimaryKey @ColumnInfo(name="placeid") val id:String= UUID.randomUUID().toString(),@ColumnInfo(name="placeName") val placeName:String)
