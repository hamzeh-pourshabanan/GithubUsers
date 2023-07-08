package com.example.myapplication.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.User

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query(
        "SELECT * FROM users WHERE " +
                "login LIKE :queryString "
    )
    fun usersByName(queryString: String): PagingSource<Int, User>

    @Query("DELETE FROM users")
    suspend fun clearUsers(): Unit
}