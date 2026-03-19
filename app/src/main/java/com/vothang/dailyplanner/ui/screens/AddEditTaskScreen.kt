package com.vothang.dailyplanner.ui.screens

import android.R.attr.navigationIcon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.vothang.dailyplanner.ui.components.DatePickerButton
import com.vothang.dailyplanner.viewmodel.AddEditTaskVIewModel
import com.vothang.dailyplanner.viewmodel.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskScreen(
    listId: Int? = null,
    taskId: Int? = null,
    backStack: NavBackStack<NavKey>,
    addEditViewModel: AddEditTaskVIewModel = hiltViewModel(),
    taskViewModel: TaskViewModel = hiltViewModel()
) {
    LaunchedEffect(taskId, listId) {
        if (taskId != null) {
            val task = taskViewModel.getTaskById(taskId)
            if (task != null) addEditViewModel.initForEdit(task)
        } else if (listId != null) {
            addEditViewModel.initForAdd(listId)
        }
    }

    val title by addEditViewModel.title.collectAsState()
    val note by addEditViewModel.note.collectAsState()
    val selectedListId by addEditViewModel.listId.collectAsState()
    val deadline by addEditViewModel.deadline.collectAsState()
    val lists by addEditViewModel.lists.collectAsState()

    var showTitleError by remember { mutableStateOf(false) }
    var dropdownExpanded by remember { mutableStateOf(false) }
    val selectedListName = lists.find { it.id == selectedListId }?.name ?: "Chọn danh sách"

    val screenTitle = if (taskId != null) "Sửa task" else "Thêm task"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    IconButton(onClick = { backStack.removeLastOrNull() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Quay lại")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    addEditViewModel.onTitleChange(it)
                    if (showTitleError) showTitleError = false
                },
                label = { Text("Tiêu đề") },
                isError = showTitleError && title.isBlank(),
                supportingText = {
                    if (showTitleError && title.isBlank()) {
                        Text("Tiêu đề không được để trống")
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = note,
                onValueChange = { addEditViewModel.onNoteChange(it) },
                label = { Text("Ghi chú") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            ExposedDropdownMenuBox(
                expanded = dropdownExpanded,
                onExpandedChange = { dropdownExpanded = it }
            ) {
                OutlinedTextField(
                    value = selectedListName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Danh sách") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                )
                ExposedDropdownMenu(
                    expanded = dropdownExpanded,
                    onDismissRequest = { dropdownExpanded = false }
                ) {
                    lists.forEach { taskList ->
                        DropdownMenuItem(
                            text = { Text(taskList.name) },
                            onClick = {
                                addEditViewModel.onListIdChange(taskList.id)
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }

            // Chọn deadline
            DatePickerButton(
                selectedTimestamp = deadline,
                onDateSelected = { addEditViewModel.onDeadlineChange(it) }
            )

            Button(
                onClick = {
                    showTitleError = true
                    val saved = addEditViewModel.saveTask()
                    if (saved) backStack.removeLastOrNull()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Lưu")
            }
        }
    }
}


















