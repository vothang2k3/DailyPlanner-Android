package com.vothang.dailyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vothang.dailyplanner.data.repository.TaskListRepository
import com.vothang.dailyplanner.data.repository.TaskRepository
import com.vothang.dailyplanner.model.Task
import com.vothang.dailyplanner.model.TaskList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskVIewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskListTaskRepository: TaskListRepository
): ViewModel() {
/*  val id: Int = 0,
    val title: String,
    val note: String = "",
    val listId: Int,
    val isDone: Boolean = false,
    val deadline: Long? = null */

    // State của form
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()
    private val _note = MutableStateFlow("")
    val note: StateFlow<String> = _note.asStateFlow()
    private val _listId = MutableStateFlow<Int?>(null)
    val listId: StateFlow<Int?> = _listId.asStateFlow()
    private val _deadline = MutableStateFlow<Long?>(null)
    val deadline: StateFlow<Long?> = _deadline.asStateFlow()
    private val _isReminderOn = MutableStateFlow(false)
    val isReminderOn: StateFlow<Boolean> = _isReminderOn.asStateFlow()

    //taskId != null nghĩa là đang edit, null nghĩa là đang thêm mới
    private var editingTaskId: Int? = null

    val lists: StateFlow<List<TaskList>> = taskListTaskRepository.getLists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onTitleChange(value: String) { _title.value = value }
    fun onNoteChange(value: String) { _note.value = value }
    fun onListIdChange(value: Int) { _listId.value = value }
    fun onDeadlineChange(value: Long?) { _deadline.value = value }
    fun onReminderChange(value: Boolean) { _isReminderOn.value = value }

    // Gọi khi mở màn Add - set listId mặc định từ trang hiện tại
    fun initForAdd(listId: Int) {
        editingTaskId = null
        _listId.value = listId
        _title.value = ""
        _note.value = ""
        _deadline.value = null
        _isReminderOn.value = false
    }

    // Gọi khi mở màn Edit - load data cũ vào form
    fun initForEdit(task: Task) {
        editingTaskId = task.id
        _listId.value = task.listId
        _title.value = task.title
        _note.value = task.note
        _deadline.value = task.deadline
        _isReminderOn.value = false
    }

    // Trả về true nếu save thành công, false nếu validation fail
    fun saveTask(): Boolean {
        val currentTitle = _title.value.trim()
        if(currentTitle.isBlank()) return false

        val currentList = listId.value ?: return false

        val task = Task(
            title = currentTitle,
            id = editingTaskId ?: 0,
            note = _note.value.trim(),
            isDone = false,
            deadline = _deadline.value,
            listId = currentList,
        )

        viewModelScope.launch {
            if(editingTaskId == null) {
                taskRepository.insertTask(task)
            } else {
                taskRepository.updateTask(task)
            }
        }

        return true
    }
}