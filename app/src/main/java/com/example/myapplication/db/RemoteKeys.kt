package com.example.myapplication.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
class RemoteKeys(
    @PrimaryKey val repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?
) {
}