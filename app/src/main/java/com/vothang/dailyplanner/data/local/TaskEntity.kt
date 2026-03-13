package com.vothang.dailyplanner.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vothang.dailyplanner.model.Task

@Entity(tableName = "tasks")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val note: String,
    val isDone: Boolean,
    val deadline: Long?,
    val listId: Int
) {
    // Chuyển Entity -> Domain model để dùng trong ViewModel/UI
    fun toTask(): Task = Task(
        id = id,
        title = title,
        isDone = isDone,
        note = note,
        deadline = deadline,
        listId = listId
    )
}

// Extension function: chuyển Domain Model -> Entity để lưu vào Room
fun Task.toEntity() : TaskEntity = TaskEntity(
    id = id,
    title = title,
    isDone = isDone,
    note = note,
    deadline = deadline,
    listId = listId
)