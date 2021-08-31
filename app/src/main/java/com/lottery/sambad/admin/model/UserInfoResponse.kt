package com.lottery.sambad.admin.model

data class UserInfoResponse(
    val status: String?=null,
    val message: String?=null,
    val data: MutableList<ModelUsers>?=null
)
