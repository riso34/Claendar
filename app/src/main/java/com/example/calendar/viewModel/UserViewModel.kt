package com.example.calendar.viewModel

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.calendar.UserApplication
import com.example.calendar.model.User
import com.example.calendar.model.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _showMainView = MutableStateFlow(false)
    val showMainView: Flow<Boolean> = _showMainView.asStateFlow()
    private val _showNewRegistration = MutableStateFlow(false)
    val showNewRegistration: Flow<Boolean> = _showNewRegistration.asStateFlow()
    private val _currentUserPassword = MutableStateFlow<User?>(null)// パスワード確認用の状態
    val currentUserPassword = _currentUserPassword.asStateFlow()


    // DB用関数
    fun getPassword(email: String) = userRepository.getPassword(email)

    fun insert(user: User) = viewModelScope.launch {
        userRepository.insert(user)
    }

    fun delete(user: User) = viewModelScope.launch {
        userRepository.delete(user)
    }

    // UI用関数
    fun isLogin(inputPassword: String, users: List<User>):Boolean {
        val isValid = users.any{ it.password == inputPassword }
        if (isValid) {
            _showMainView.value = true
            _currentUserPassword.value = users.find { it.password == inputPassword }
        }
        return isValid
    }
    fun onShowMainView() {
        _showMainView.value = true
    }

    fun onShowNewRegistration() {
        _showNewRegistration.value = true

    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as UserApplication)
                UserViewModel(application.container.userRepository)
            }
        }
    }
}