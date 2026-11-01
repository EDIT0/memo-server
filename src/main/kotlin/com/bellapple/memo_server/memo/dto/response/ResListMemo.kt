package com.bellapple.memo_server.memo.dto.response

import com.bellapple.memo_server.memo.Memo
import java.time.Instant

// 메모 목록의 한 항목 응답
data class ResListMemo(
    val id: Long,
    val userId: Long, // 메모를 쓴 유저 id
    val content: String,
    val createAt: Instant,
    val updateAt: Instant
) {
    companion object {
        // Entity -> 응답 DTO
        fun from(memo: Memo): ResListMemo {
            return ResListMemo(
                id = memo.id!!,
                userId = memo.user.id!!,
                content = memo.content,
                createAt = memo.createdAt,
                updateAt = memo.updatedAt
            )
        }
    }
}
