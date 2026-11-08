package com.bellapple.memo_server.profileimage

import com.bellapple.memo_server.profileimage.dto.request.ReqDeleteProfileImage
import com.bellapple.memo_server.profileimage.dto.request.ReqGetProfileImage
import com.bellapple.memo_server.profileimage.dto.request.ReqUploadProfileImage
import com.bellapple.memo_server.profileimage.dto.response.ResDeleteProfileImage
import com.bellapple.memo_server.profileimage.dto.response.ResUploadProfileImage
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/profile-image")
class ProfileImageController(
    private val profileImageService: ProfileImageService
) {
    @PostMapping("/upload")
    fun uploadProfileImage(@ModelAttribute request: ReqUploadProfileImage): ResUploadProfileImage {
        return ResUploadProfileImage.from(
            profileImageService.uploadProfileImage(request.userId, request.file)
        )
    }

    @GetMapping("/detail")
    fun getProfileImage(@ModelAttribute request: ReqGetProfileImage): ResponseEntity<ByteArray> {
        val profileImage = profileImageService.getProfileImage(request.userId)
        val bytes = profileImageService.readFile(profileImage)
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(profileImage.contentType))
            .body(bytes)
    }

    @PostMapping("/delete")
    fun deleteProfileImage(@RequestBody request: ReqDeleteProfileImage): ResDeleteProfileImage {
        profileImageService.deleteProfileImage(request.userId)
        return ResDeleteProfileImage(userId = request.userId)
    }
}