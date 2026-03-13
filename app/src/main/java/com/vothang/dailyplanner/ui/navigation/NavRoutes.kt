package com.vothang.dailyplanner.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object TaskList : NavKey

@Serializable
object AddTask : NavKey

@Serializable
data class TaskDetail(val taskId: Int) : NavKey

@Serializable
data class EditTask(val taskId: Int) : NavKey