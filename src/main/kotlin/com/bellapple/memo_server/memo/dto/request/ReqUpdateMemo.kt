package com.bellapple.memo_server.memo.dto.request

data class ReqUpdateMemo(
    val id: Long,
    val content: String
)