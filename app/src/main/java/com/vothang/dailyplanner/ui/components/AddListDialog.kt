package com.vothang.dailyplanner.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AddListDialog(
    onConfirm: (name: String) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Thêm danh sách mới") },
        text = {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tên danh sách") },
                singleLine = true
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if(name.isNotBlank()) {
                        onConfirm(name.trim())
                    }
                }
            ) {
                Text("Tạo")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Hủy")
            }
        },
    )
}