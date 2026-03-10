package com.vothang.dailyplanner.data

import com.vothang.dailyplanner.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    // Trả về Flow - Tự động cập nhật khi DB thay đổi
    fun getTasks() : Flow<List<Task>>

    // Suspend function - chạy bất đồng bộ
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(taskId: Int)
}