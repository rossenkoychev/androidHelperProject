package com.example.rossen.androidadvancedlearning.users.usersdb

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "users")
data class User(@ColumnInfo(name = "username")
                val userName: String) {
    @PrimaryKey
    @ColumnInfo(name = "userid")
    val id: String = UUID.randomUUID().toString()
}
