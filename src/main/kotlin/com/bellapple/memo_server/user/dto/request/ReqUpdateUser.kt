package com.bellapple.memo_server.user.dto.request

data class ReqUpdateUser(
    val id: Long,
    val username: String,
    val email: String,
    val password: String
)
