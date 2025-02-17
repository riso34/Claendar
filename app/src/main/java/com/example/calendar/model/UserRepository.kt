package com.example.calendar.model

class UserRepository(private val userDao: UserDao) {

    // passwordを取得
    fun getPassword(email: String) = userDao.getPassword(email)

    //
    suspend fun insert(user: User) = userDao.insert(user)

    //
    suspend fun delete(user: User) = userDao.delete(user)
}