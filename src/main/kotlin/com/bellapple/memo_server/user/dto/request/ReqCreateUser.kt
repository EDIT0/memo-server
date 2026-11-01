package com.bellapple.memo_server.user.dto.request

data class ReqCreateUser(
    val username: String,
    val email: String,
    val password: String
)
