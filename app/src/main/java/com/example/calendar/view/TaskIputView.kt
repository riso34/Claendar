package com.example.calendar.view

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Keyboard
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calendar.viewModel.UserViewModel
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskRegistrationDialog(
    onDismiss: () -> Unit,
    onTaskAdd: (String, String, LocalDateTime) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDateTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "タスクの登録",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("タイトル") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("説明") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 日付選択ボタン
                OutlinedButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")))
                }

                // 時間選択ボタン
                OutlinedButton(
                    onClick = { showTimePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(selectedDate.format(DateTimeFormatter.ofPattern("HH:mm")))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("キャンセル")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            onTaskAdd(title, description, selectedDate)
                        },
                        enabled = title.isNotBlank()
                    ) {
                        Text("登録")
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerModal(
            onDismiss = {showDatePicker = false},
            onDateSelected = { dateMillis ->
                if (dateMillis != null) {
                    selectedDate =
                        LocalDateTime.ofEpochSecond(dateMillis / 1000, 0, ZoneOffset.UTC)
                }
                showDatePicker = false
            }
        )
    }

    if (showTimePicker) {
        timePickerDialog (
            onDismiss = { showTimePicker = false },
            onConfirm = {TimePickerState ->
                selectedDate = selectedDate.withHour(TimePickerState.hour)
                selectedDate = selectedDate.withMinute(TimePickerState.minute)
                showTimePicker = false
            }
        )


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun timePickerDialog(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    var showDail by remember { mutableStateOf(true) }
    val toggleIcon = if (showDail) {
        Icons.Outlined.Keyboard
    } else {
        Icons.Outlined.AccessTime
    }

    TimePickerDialog(
        onDismiss = { onDismiss() },
        onConfirm = { onConfirm(timePickerState) },
        toggle = {
            IconButton(onClick = { showDail = !showDail }) {
                Icon(
                    imageVector = toggleIcon,
                    contentDescription = "Time picker type toggle"
                )
            }
        }
    ) {
        if (showDail) {
            TimePicker(state = timePickerState)
        } else {
            TimeInput(state = timePickerState)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    title: String = "Select Time",
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier.width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(shape = MaterialTheme.shapes.extraLarge,
                            color = MaterialTheme.colorScheme.surface
                ),
        ) {
           Column(
               modifier = Modifier.padding(24.dp),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               Text(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(bottom = 20.dp),
                   text = title,
                   style = MaterialTheme.typography.labelMedium
               )
               content()
               Row(
                   modifier = Modifier
                       .height(40.dp)
                       .fillMaxWidth()
               ) {
                   toggle()
                   Spacer(modifier = Modifier.weight(1f))
                   TextButton(onClick = onDismiss) { Text("Cancel") }
                   TextButton(onClick = onConfirm) { Text("OK") }
               }
           }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}