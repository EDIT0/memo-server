package com.bellapple.memo_server.profileimage.dto.request

import org.springframework.web.multipart.MultipartFile

data class ReqUploadProfileImage(
    val userId: Long,
    val file: MultipartFile
)
