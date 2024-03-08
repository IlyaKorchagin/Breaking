package com.korchagin.breaking.presentation.screens.common

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ExitAlertDialog(
    onConfirmExit: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Выход") },
        text = { Text("Вы уверены, что хотите выйти из приложения?") },
        confirmButton = {
            Button(
                onClick = {
                    onConfirmExit()
                }
            ) {
                Text("Выйти")
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Нет")
            }
        }
    )
}