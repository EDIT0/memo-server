package com.bellapple.memo_server.memo.dto.response

import com.bellapple.memo_server.memo.Memo
import java.time.Instant

// 메모 수정 응답
data class ResUpdateMemo(
    val id: Long,
    val userId: Long, // 메모를 쓴 유저 id
    val content: String,
    val createAt: Instant,
    val updateAt: Instant
) {
    companion object {
        // Entity -> 응답 DTO
        fun from(memo: Memo): ResUpdateMemo {
            return ResUpdateMemo(
                id = memo.id!!,
                userId = memo.user.id!!,
                content = memo.content,
                createAt = memo.createdAt,
                updateAt = memo.updatedAt
            )
        }
    }
}
