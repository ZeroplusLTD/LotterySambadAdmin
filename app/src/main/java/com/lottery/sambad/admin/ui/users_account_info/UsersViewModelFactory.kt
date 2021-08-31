package com.lottery.sambad.admin.ui.users_account_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lottery.sambad.admin.database.MyApi

class UsersViewModelFactory(private val myApi: MyApi): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(myApi) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}