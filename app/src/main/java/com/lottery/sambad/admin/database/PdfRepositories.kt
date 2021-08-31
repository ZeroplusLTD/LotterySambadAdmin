package com.lottery.sambad.admin.database

import com.lottery.sambad.admin.model.UserInfoResponse
import retrofit2.Response

class PdfRepositories {

    suspend fun getDuplicateLotteryNumberList(pageNumber: String, itemCount: String,listPosition: String, myApi: MyApi): Response<UserInfoResponse> {
        return myApi.getDuplicateLotteryNumberList(pageNumber,itemCount,listPosition)
    }


}