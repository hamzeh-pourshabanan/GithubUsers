package com.example.myapplication.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.model.User

@Database(
    entities = [User::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(TypeAdapters::class)
abstract class UserDatabase: RoomDatabase() {

    abstract fun usersDao(): UsersDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {

        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getInstance(context: Context): UserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UserDatabase::class.java, "Github.db"
            )
                .build()
    }
}