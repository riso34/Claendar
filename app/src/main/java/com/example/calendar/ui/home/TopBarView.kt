package com.example.calendar.ui.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.calendar.viewModel.TaskViewModel

@Composable
fun TopBarView(
    viewModel: TaskViewModel,
) {
    Text(viewModel.userId.toString())
}