package com.example.calendar.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.calendar.model.User
import com.example.calendar.model.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _currentUserName = MutableStateFlow<User?>(null)// パスワード確認用の状態
    val currentUserName = _currentUserName.asStateFlow()
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
        val isValid = users.any{ it.userPassword == inputPassword }
        if (isValid) {
            _currentUserPassword.value = users.find { it.userPassword == inputPassword }
            _currentUserName.value = users.find { it.userPassword == inputPassword }
        }
        return isValid
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as com.example.calendar.Application)
                UserViewModel(application.usercontainer.userRepository)
            }
        }
    }
}