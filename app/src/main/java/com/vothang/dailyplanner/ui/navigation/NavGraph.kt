package com.vothang.dailyplanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation.NavGraph
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.vothang.dailyplanner.ui.screens.AddEditTaskScreen
import com.vothang.dailyplanner.ui.screens.TaskDetailScreen
import com.vothang.dailyplanner.ui.screens.TaskListScreen
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator


@Composable
fun NavGraph(backStack : NavBackStack<NavKey>) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<TaskList> {
                TaskListScreen()
            }

            entry<AddTask> {
                /* Xử lý sau */
                AddEditTaskScreen()
            }

            entry<TaskDetail> { screen ->
                /* Xử lý sau */
                TaskDetailScreen(taskId = screen.taskId)
            }

            entry<EditTask> { screen ->
                /* Xử lý sau */
                AddEditTaskScreen(taskId = screen.taskId)
            }
        }
    )
}

