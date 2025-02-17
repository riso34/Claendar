package com.example.calendar

import android.app.Application

class Application: Application() {
    lateinit var usercontainer: UserContainer
    lateinit var taskcontainer: TaskContainer

    override fun onCreate() {
        super.onCreate()
        usercontainer = UserContainer(this)
        taskcontainer = TaskContainer(this)

    }
}