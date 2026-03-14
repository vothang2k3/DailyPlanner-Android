package com.vothang.dailyplanner.data.repository

import androidx.room.Dao
import com.vothang.dailyplanner.model.TaskList
import kotlinx.coroutines.flow.Flow

interface TaskListRepository {
    fun getLists(): Flow<List<TaskList>>
    suspend fun insertList(list: TaskList)
    suspend fun deleteList(list: TaskList)
}