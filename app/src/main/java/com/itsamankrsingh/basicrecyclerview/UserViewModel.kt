package com.itsamankrsingh.basicrecyclerview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.itsamankrsingh.basicrecyclerview.data.User
import com.itsamankrsingh.basicrecyclerview.data.UserDatabase
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {

    val readAllData:LiveData<List<User>>
    private val repository:UserRepository

    init {
        val userDao=UserDatabase.getDatabase(application).userDao()
        repository= UserRepository(userDao)
        readAllData=repository.readAllData
    }

    fun addUser(user:User){
        viewModelScope.launch {
            repository.addUser(user)
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch {
            repository.deleteUser(user)
        }
    }
    fun deleteAllUser(){
        viewModelScope.launch {
            repository.deleteAllUser()
        }
    }
}