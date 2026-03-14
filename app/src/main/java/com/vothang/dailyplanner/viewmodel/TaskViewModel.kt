package com.vothang.dailyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vothang.dailyplanner.data.repository.TaskRepository
import com.vothang.dailyplanner.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.listOf

class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository
): ViewModel() {
    // Chuyển Flow từ repository thành StateFlow để UI collect
    val tasks: StateFlow<List<Task>> = taskRepository.getTasks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insert(task: Task) {
        viewModelScope.launch {
            taskRepository.insertTask(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
        }
    }

    fun delete(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }

    fun toggleDone(task: Task) {
        viewModelScope.launch {
            taskRepository.updateTask(task.copy(isDone = !task.isDone))
        }
    }

    fun getTaskById(id: Int): Task? {
        return tasks.value.find {it.id == id}
    }
}