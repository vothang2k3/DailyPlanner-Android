package com.vothang.dailyplanner.data.repository

import com.vothang.dailyplanner.data.local.TaskDao
import com.vothang.dailyplanner.data.local.toEntity
import com.vothang.dailyplanner.model.Task
import com.vothang.dailyplanner.model.TaskList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository{
    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getAll().map { entities -> // Đây là Flow.map
            entities.map { it.toTask() }          // Đây là List.map
        }
    }

    override fun getTasksByListId(listId: Int): Flow<List<Task>> {
        return taskDao.getByListId(listId).map { entities ->
            entities.map { it.toTask() }
        }
    }

    override suspend fun insertTask(task: Task) {
        taskDao.insert(task.toEntity())
    }

    override suspend fun updateTask(task: Task) {
        taskDao.update(task.toEntity())
    }

    override suspend fun deleteTask(task: Task) {
        taskDao.delete(task.toEntity())
    }
}