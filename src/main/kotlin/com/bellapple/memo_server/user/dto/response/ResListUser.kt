package com.bellapple.memo_server.user.dto.response

import com.bellapple.memo_server.user.User
import java.time.Instant

// 유저 목록의 한 항목 응답 (password는 응답에 넣지 않음)
data class ResListUser(
    val id: Long,
    val username: String,
    val email: String,
    val createAt: Instant,
    val updateAt: Instant
) {
    companion object {
        // Entity -> 응답 DTO
        fun from(user: User): ResListUser {
            return ResListUser(
                id = user.id!!,
                username = user.username,
                email = user.email,
                createAt = user.createdAt,
                updateAt = user.updatedAt
            )
        }
    }
}
