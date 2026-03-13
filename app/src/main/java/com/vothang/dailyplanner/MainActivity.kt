package com.vothang.dailyplanner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import com.vothang.dailyplanner.ui.navigation.NavGraph
import com.vothang.dailyplanner.ui.navigation.TaskList
import com.vothang.dailyplanner.ui.screens.TaskListScreen
import com.vothang.dailyplanner.ui.theme.DailyPlannerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DailyPlannerTheme() {
                val backStack = rememberNavBackStack(TaskList)
                NavGraph(backStack = backStack)
            }
        }
    }
}