package com.lottery.sambad.admin.ui.users_account_info

import androidx.lifecycle.ViewModel
import com.lottery.sambad.admin.database.MyApi
import com.lottery.sambad.admin.database.PdfRepositories

class UsersViewModel(var myApi: MyApi): ViewModel() {


    suspend fun getUsersList(pageNumber: String, itemCount: String,listPosition: String) =
        PdfRepositories().getDuplicateLotteryNumberList(pageNumber,itemCount,listPosition,myApi)









}