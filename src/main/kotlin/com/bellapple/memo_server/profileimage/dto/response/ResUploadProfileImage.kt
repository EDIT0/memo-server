package com.bellapple.memo_server.profileimage.dto.response

import com.bellapple.memo_server.profileimage.table.ProfileImage
import java.time.Instant

data class ResUploadProfileImage(
    val id: Long,
    val userId: Long,
    val originalName: String,
    val contentType: String,
    val size: Long,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    companion object {
        fun from(profileImage: ProfileImage): ResUploadProfileImage {
            return ResUploadProfileImage(
                id = profileImage.id!!,
                userId = profileImage.user.id!!,
                originalName = profileImage.originalName,
                contentType = profileImage.contentType,
                size = profileImage.size,
                createdAt = profileImage.createdAt,
                updatedAt = profileImage.updatedAt
            )
        }
    }
}
