package com.vothang.dailyplanner.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vothang.dailyplanner.data.repository.TaskListRepository
import com.vothang.dailyplanner.data.repository.TaskRepository
import com.vothang.dailyplanner.model.Task
import com.vothang.dailyplanner.model.TaskList
import com.vothang.dailyplanner.service.AlarmScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskListRepository: TaskListRepository,
    private val alarmScheduler: AlarmScheduler
) : ViewModel() {

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

    private var editingTaskId: Int? = null

    val lists: StateFlow<List<TaskList>> = taskListRepository.getLists()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun onTitleChange(value: String) { _title.value = value }
    fun onNoteChange(value: String) { _note.value = value }
    fun onListIdChange(value: Int) { _listId.value = value }
    fun onDeadlineChange(value: Long?) {
        _deadline.value = value
        // Tắt reminder tự động nếu xoá deadline
        if (value == null) _isReminderOn.value = false
    }
    fun onReminderChange(value: Boolean) { _isReminderOn.value = value }

    fun initForAdd(listId: Int) {
        editingTaskId = null
        _listId.value = listId
        _title.value = ""
        _note.value = ""
        _deadline.value = null
        _isReminderOn.value = false
    }

    fun initForEdit(task: Task) {
        editingTaskId = task.id
        _title.value = task.title
        _note.value = task.note
        _listId.value = task.listId
        _deadline.value = task.deadline
        _isReminderOn.value = false
    }

    // Trả về Task nếu save thành công, null nếu validation fail
    fun saveTask(): Task? {
        val currentTitle = _title.value.trim()
        if (currentTitle.isBlank()) return null

        val currentListId = _listId.value ?: return null

        val task = Task(
            id = editingTaskId ?: 0,
            title = currentTitle,
            note = _note.value.trim(),
            listId = currentListId,
            deadline = _deadline.value,
            isDone = false
        )

        viewModelScope.launch {
            if (editingTaskId == null) {
                taskRepository.insertTask(task)
            } else {
                taskRepository.updateTask(task)
            }
        }
        return task
    }

    fun scheduleReminder(task: Task) {
        val deadline = task.deadline ?: return
        // Nhắc trước deadline 1 tiếng
        val triggerAt = deadline - 60 * 60 * 1000L
        if (triggerAt > System.currentTimeMillis()) {
            alarmScheduler.schedule(task.id, triggerAt)
        }
    }

    fun cancelReminder(taskId: Int) {
        alarmScheduler.cancel(taskId)
    }
}