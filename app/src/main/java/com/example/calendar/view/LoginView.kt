package com.example.calendar.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calendar.model.User
import com.example.calendar.ui.theme.CalendarTheme
import com.example.calendar.viewModel.UserViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    viewModel: UserViewModel = viewModel(factory = UserViewModel.Factory),
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val users by viewModel.getPassword(email).collectAsState(initial = emptyList())
    val showNewRegistration by viewModel.showNewRegistration.collectAsState(initial = false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (showNewRegistration) {
            Text(
                text = "新規登録",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    isError = false
                },
                label = { Text("ニックネーム") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                isError = isError
            )

        } else {
            Text(
                text = "ログイン",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 32.dp)
            )
        }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isError = false
            },
            label = { Text("メールアドレス") },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = isError
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                isError = false
            },
            label = { Text("パスワード") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            isError = isError
        )

        if (showNewRegistration) {
            Button(
                onClick = {
                    viewModel.insert(User(name = name, email = email, password = password))
                    viewModel.onShowMainView()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
            ) {
                Text("登録")
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth().height(50.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { viewModel.onShowNewRegistration() },
                    modifier = Modifier.padding(end = 32.dp)
                ) {
                    Text("新規登録")
                }
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            if (!viewModel.isLogin(password, users)) {
                                isError = true
                            }
                        } else {
                            isError = true
                        }
                    },
                    modifier = Modifier.padding(end = 32.dp)
                ) {
                    Text("ログイン")
                }
            }
        }

        if (isError) {
            if (showNewRegistration) {
                Text(
                    text = "ニックネームとメールアドレス、パスワードを再入力してください",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                Text(
                    text = "メールアドレスとパスワードを再入力してください",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
