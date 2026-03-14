package com.vothang.dailyplanner.di

import android.content.Context
import androidx.room.Room
import com.vothang.dailyplanner.data.local.AppDatabase
import com.vothang.dailyplanner.data.local.TaskDao
import com.vothang.dailyplanner.data.local.TaskListDao
import com.vothang.dailyplanner.model.Task
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "daily_planner"
        ).build()
    }

    @Provides
    fun provideTaskDao(database: AppDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    fun provideTaskListDao(database: AppDatabase): TaskListDao {
        return database.taskListDao()
    }
}