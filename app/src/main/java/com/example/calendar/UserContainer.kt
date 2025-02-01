package com.example.calendar

import android.content.Context
import com.example.calendar.model.UserDatabase
import com.example.calendar.model.UserRepository

class UserContainer(private val context: Context) {
    val userRepository: UserRepository by lazy {
        UserRepository(UserDatabase.getDatabase(context).userDao())
    }
}