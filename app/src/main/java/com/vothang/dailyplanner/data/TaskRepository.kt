package com.vothang.dailyplanner.data

import com.vothang.dailyplanner.model.Task

interface TaskRepository {
    fun getTasks() : List<Task>
    fun insertTask(task: Task)
    fun updateTask(task: Task)
    fun deleteTask(taskId: Int)
}