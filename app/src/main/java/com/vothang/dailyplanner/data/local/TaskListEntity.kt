package com.vothang.dailyplanner.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vothang.dailyplanner.model.TaskList

@Entity(tableName = "task_lists")
data class TaskListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String
) {
    fun toTaskList() : TaskList = TaskList(
        id = id,
        name = name
    )
}

fun TaskList.toTaskListEntity() : TaskListEntity = TaskListEntity(
    id = id,
    name = name
)
