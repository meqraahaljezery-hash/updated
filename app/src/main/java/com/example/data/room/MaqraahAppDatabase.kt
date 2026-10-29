package com.example.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class, MessageEntity::class, TransactionEntity::class], version = 1, exportSchema = false)
abstract class MaqraahAppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun messageDao(): MessageDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: MaqraahAppDatabase? = null

        fun getDatabase(context: Context): MaqraahAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MaqraahAppDatabase::class.java,
                    "maqraah_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
