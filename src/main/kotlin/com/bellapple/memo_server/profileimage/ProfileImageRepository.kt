package com.bellapple.memo_server.profileimage

import com.bellapple.memo_server.profileimage.table.ProfileImage

interface ProfileImageRepository {
    fun save(profileImage: ProfileImage): ProfileImage
    fun findByUserId(userId: Long): ProfileImage?
    fun update(profileImage: ProfileImage): ProfileImage
    fun delete(profileImage: ProfileImage)
}