package com.vothang.dailyplanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TaskDao::class, TaskListDao::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskListDao(): TaskListDao
}