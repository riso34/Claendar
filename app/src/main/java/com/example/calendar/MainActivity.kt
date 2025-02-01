package com.example.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calendar.ui.theme.CalendarTheme
import com.example.calendar.view.CalendarCompose
import com.example.calendar.view.FABView
import com.example.calendar.view.LoginScreen
import com.example.calendar.view.NavDrawerView
import com.example.calendar.viewModel.TaskViewModel
import com.example.calendar.viewModel.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val taskViewModel: TaskViewModel = viewModel()
            val loginViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory)

            val showMainView by loginViewModel.showMainView.collectAsState(initial = false)

            CalendarTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(loginViewModel)
                    if (showMainView) {
                        NavDrawerView { paddingValues ->
                            CalendarCompose(paddingValues)
                            FABView(taskViewModel)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val loginViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory)

    CalendarTheme {
        LoginScreen(loginViewModel)
    }
}
