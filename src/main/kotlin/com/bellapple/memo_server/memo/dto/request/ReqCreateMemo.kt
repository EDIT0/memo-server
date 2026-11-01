package com.bellapple.memo_server.memo.dto.request

data class ReqCreateMemo(
    val userId: Long,   // 메모를 쓰는 유저 id
    val content: String
)
