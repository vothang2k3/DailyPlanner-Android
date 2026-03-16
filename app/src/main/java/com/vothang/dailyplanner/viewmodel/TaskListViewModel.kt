package com.vothang.dailyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vothang.dailyplanner.data.repository.TaskListRepository
import com.vothang.dailyplanner.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val taskListRepository: TaskListRepository
): ViewModel() {
    val lists: StateFlow<List<TaskList>> = taskListRepository.getLists().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun insertList(name: String) {
        viewModelScope.launch {
            taskListRepository.insertList(TaskList(name = name))
        }
    }

    fun deleteList(list: TaskList) {
        viewModelScope.launch {
            taskListRepository.deleteList(list)
        }
    }
}