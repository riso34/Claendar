package com.example.calendar.model

class TaskRepository(private val taskDao: TaskDao) {

    //
    fun getTasks(searchUser: String) = taskDao.getTasks(searchUser)

    //
    suspend fun insertTask(task: Task) = taskDao.insert(task)

    //
    suspend fun deleteTask(task: Task) = taskDao.delete(task)
}