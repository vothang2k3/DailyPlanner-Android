package com.vothang.dailyplanner.ui.components

import android.app.DatePickerDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerButton(
    selectedTimestamp: Long?,
    onDateSelected: (Long?) -> Unit
) {
    var showDialog by remember { mutableStateOf(false)}
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = selectedTimestamp
    )

    val label = if(selectedTimestamp != null) {
        SimpleDateFormat("dd/MM/yyyy", Locale.forLanguageTag("vi-VN"))
            .format(Date(selectedTimestamp))
    } else {
        "Chọn deadline"
    }

    OutlinedButton(onClick = { showDialog = true }) {
        Icon(Icons.Default.DateRange, contentDescription = null)
        Text(text = label)
    }

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDateSelected(datePickerState.selectedDateMillis)
                        showDialog = false
                    }
                ) {
                    Text("Xác nhận")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Huỷ")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}