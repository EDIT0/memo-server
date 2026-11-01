package com.bellapple.memo_server.user.dto.response

import com.bellapple.memo_server.user.User
import java.time.Instant

// 유저 수정 응답 (password는 응답에 넣지 않음)
data class ResUpdateUser(
    val id: Long,
    val username: String,
    val email: String,
    val createAt: Instant,
    val updateAt: Instant
) {
    companion object {
        // Entity -> 응답 DTO
        fun from(user: User): ResUpdateUser {
            return ResUpdateUser(
                id = user.id!!,
                username = user.username,
                email = user.email,
                createAt = user.createdAt,
                updateAt = user.updatedAt
            )
        }
    }
}
