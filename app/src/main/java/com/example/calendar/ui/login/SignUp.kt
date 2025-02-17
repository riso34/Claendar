package com.example.calendar.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calendar.ui.theme.CalendarTheme
import com.example.calendar.viewModel.UserViewModel

@Composable
fun SignUpView(
    viewModel: UserViewModel = viewModel(factory = UserViewModel.Factory),
    onLoginClick: (name:String) -> Unit,
    onSignInClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    val users by viewModel.getPassword(email).collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ログイン",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

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

        Row(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = onSignInClick ,
                modifier = Modifier.padding(end = 24.dp)
            ) {
                Text("新規登録")
            }
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        if (!viewModel.isLogin(password, users)) {
                            isError = true
                        } else {
                            onLoginClick(viewModel.currentUserName.value?.userName.toString())
                        }
                    } else {
                        isError = true
                    }
                },
                modifier = Modifier.padding(end = 24.dp)
            ) {
                Text("ログイン")
            }
        }

        if (isError) {
            Text(
                text = "メールアドレスとパスワードを再入力してください",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SignUpViewPreview() {
    CalendarTheme {
        SignUpView(onLoginClick = {}, onSignInClick = {})
    }
}