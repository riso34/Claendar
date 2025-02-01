package com.example.calendar.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

// TaskViewModel.kt
class TaskViewModel : ViewModel() {
    private val _showTaskDialog = MutableStateFlow(false)
    val showTaskDialog: StateFlow<Boolean> = _showTaskDialog.asStateFlow()

    fun onShowTaskDialog() {
        _showTaskDialog.value = true
    }

    fun onDismissTaskDialog() {
        _showTaskDialog.value = false
    }

    fun addTask(title: String, description: String, date: LocalDateTime) {
        // TODO: タスクの登録処理を実装
        _showTaskDialog.value = false
    }
}