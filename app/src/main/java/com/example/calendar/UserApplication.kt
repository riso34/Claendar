package com.example.calendar

import android.app.Application

class UserApplication: Application() {
    lateinit var container: UserContainer
    override fun onCreate() {
        super.onCreate()
        container = UserContainer(this)
    }
}