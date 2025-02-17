package com.example.calendar.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.calendar.viewModel.TaskViewModel

@Composable
fun MemoScreen(
    viewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory),
    navController: NavHostController,
    onScheduleClick: () -> Unit,
    onMemoClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopBarView(
                viewModel
            )
        },
        bottomBar = {
            NavBottomBarView(navController)
        },
    ) { paddingValues ->
        Text(text = "home screen", modifier = Modifier.padding(paddingValues))
    }
}