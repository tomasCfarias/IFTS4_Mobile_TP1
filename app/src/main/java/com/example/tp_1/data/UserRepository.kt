package com.example.tp_1.data

import androidx.lifecycle.LiveData

class UserRepository () {

    private val userDao = UserDB.getDatabase().userDao()

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User) {
        userDao.addUser(user = user)
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }


    suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }


    suspend fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}