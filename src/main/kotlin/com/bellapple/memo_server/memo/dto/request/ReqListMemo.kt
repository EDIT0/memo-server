package com.bellapple.memo_server.memo.dto.request

data class ReqListMemo(
    val userId: Long // 이 유저의 메모만 조회
)
