package com.vothang.dailyplanner.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import com.vothang.dailyplanner.model.Task
import com.vothang.dailyplanner.ui.components.AddListDialog
import com.vothang.dailyplanner.ui.components.EmptyTaskView
import com.vothang.dailyplanner.ui.components.TaskItem
import com.vothang.dailyplanner.ui.navigation.AddTask
import com.vothang.dailyplanner.ui.navigation.TaskDetail
import com.vothang.dailyplanner.viewmodel.TaskListViewModel
import com.vothang.dailyplanner.viewmodel.TaskViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    backStack: NavBackStack<NavKey>,
    taskViewModel: TaskViewModel = hiltViewModel(),
    taskListViewModel: TaskListViewModel = hiltViewModel()
) {
    val tasks by taskViewModel.tasks.collectAsState()
    val lists by taskListViewModel.lists.collectAsState()

    val pagerState = rememberPagerState(pageCount = { lists.size })
    val coroutineScope = rememberCoroutineScope()
    var showAddListDialog by remember { mutableStateOf(false) }

    LaunchedEffect(lists.size) {
        if (lists.isNotEmpty()) {
            pagerState.animateScrollToPage(lists.lastIndex)
        }
    }

    if (showAddListDialog) {
        AddListDialog(
            onConfirm = { name ->
                taskListViewModel.insertList(name)
                showAddListDialog = false
            },
            onDismiss = { showAddListDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Daily Plan") },
                actions = {
                    IconButton(onClick = { showAddListDialog = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Thêm danh sách")
                    }
                }
            )
        },
        floatingActionButton = {
            if (lists.isNotEmpty()) {
                FloatingActionButton(
                    onClick = {
                        val currentListId = lists[pagerState.currentPage].id
                        backStack.add(AddTask(listId = currentListId))
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Thêm task")
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (lists.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Chưa có danh sách nào. Nhấn + ở góc trên để tạo.")
                }
            } else {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    edgePadding = 0.dp
                ) {
                    lists.forEachIndexed { index, taskList ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = { Text(taskList.name) }
                        )
                    }
                }

// Trong HorizontalPager, thay LazyColumn cũ bằng:
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val currentListId = lists[page].id
                    val pageTasks = tasks.filter { it.listId == currentListId }
                    val (doneTasks, pendingTasks) = pageTasks.partition { it.isDone }
                    val sortedPending = pendingTasks.sortedWith(compareBy(nullsLast()) { it.deadline })
                    var showDoneSection by remember { mutableStateOf(true) }

                    if (pageTasks.isEmpty()) {
                        // Hiện EmptyTaskView khi chưa có task nào trong list này
                        EmptyTaskView()
                    } else {
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(
                                items = sortedPending,
                                key = { task -> task.id }
                            ) { task ->
                                SwipeToDeleteItem(onDelete = { taskViewModel.delete(task) }) {
                                    TaskItem(
                                        task = task,
                                        onToggleDone = { taskViewModel.toggleDone(it) },
                                        onClickItem = { backStack.add(TaskDetail(it.id)) }
                                    )
                                }
                            }

                            if (doneTasks.isNotEmpty()) {
                                item {
                                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { showDoneSection = !showDoneSection }
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Đã hoàn thành (${doneTasks.size})",
                                            style = MaterialTheme.typography.titleSmall,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Icon(
                                            imageVector = if (showDoneSection)
                                                Icons.Default.KeyboardArrowUp
                                            else
                                                Icons.Default.KeyboardArrowDown,
                                            contentDescription = null
                                        )
                                    }
                                }

                                items(
                                    items = doneTasks,
                                    key = { task -> "done_${task.id}" }
                                ) { task ->
                                    AnimatedVisibility(visible = showDoneSection) {
                                        SwipeToDeleteItem(onDelete = { taskViewModel.delete(task) }) {
                                            TaskItem(
                                                task = task,
                                                onToggleDone = { taskViewModel.toggleDone(it) },
                                                onClickItem = { backStack.add(TaskDetail(it.id)) }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Tách riêng để tránh lặp code SwipeToDismiss
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeToDeleteItem(
    onDelete: () -> Unit,
    content: @Composable () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(end = 16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Xoá",
                        tint = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
        }
    ) {
        content()
    }
}
