package com.vothang.dailyplanner.data.repository

import com.vothang.dailyplanner.data.local.TaskListDao
import com.vothang.dailyplanner.data.local.toTaskListEntity
import com.vothang.dailyplanner.model.TaskList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskListRepositoryImpl @Inject constructor(
    private val taskListDao: TaskListDao
): TaskListRepository {
    override fun getLists(): Flow<List<TaskList>> {
        return taskListDao.getTaskList().map { entities ->
            entities.map { it.toTaskList() }
        }
    }

    override suspend fun insertList(list: TaskList){
        taskListDao.insert(list.toTaskListEntity())
    }

    override suspend fun deleteList(list: TaskList){
        taskListDao.delete(list.toTaskListEntity())
    }
}