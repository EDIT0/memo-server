package com.bellapple.memo_server.profileimage

import com.bellapple.memo_server.profileimage.table.ProfileImage
import com.bellapple.memo_server.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.UUID

@Service
@Transactional
class ProfileImageService(
    private val profileImageRepository: ProfileImageRepository,
    private val userRepository: UserRepository
) {
    // 사진 파일을 저장할 폴더: 바탕화면/memo-uploads (user.home = /Users/내이름)
    private val uploadDir: Path = Paths.get(System.getProperty("user.home"), "Desktop", "memo-uploads")

    fun uploadProfileImage(userId: Long, file: MultipartFile): ProfileImage {
        val user = userRepository.findById(userId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. id=${userId}")

        val storedName = UUID.randomUUID().toString()
        val originalName = file.originalFilename ?: ""
        val contentType = file.contentType ?: ""

        val existing = profileImageRepository.findByUserId(userId)
        val result = if(existing == null) {
            profileImageRepository.save(
                ProfileImage(
                    user = user,
                    storedName = storedName,
                    originalName = originalName,
                    contentType = contentType,
                    size = file.size
                )
            )
        } else {
            val oldStoredName = existing.storedName
            existing.update(storedName, originalName, contentType, file.size)
            Files.deleteIfExists(uploadDir.resolve(oldStoredName)) // 예전 사진 파일 삭제
            profileImageRepository.update(existing)
        }

        Files.createDirectories(uploadDir) // 폴더가 없으면 만들기
        file.transferTo(uploadDir.resolve(storedName))

        return result
    }

    @Transactional(readOnly = true)
    fun getProfileImage(userId: Long): ProfileImage {
        return profileImageRepository.findByUserId(userId) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "프로필 사진이 없습니다. userId=${userId}")
    }

    // 디스크에서 실제 이미지 바이트를 읽음
    fun readFile(profileImage: ProfileImage): ByteArray {
        return Files.readAllBytes(uploadDir.resolve(profileImage.storedName))
    }

    fun deleteProfileImage(userId: Long) {
        val profileImage = getProfileImage(userId)
        profileImageRepository.delete(profileImage)
        Files.deleteIfExists(uploadDir.resolve(profileImage.storedName))
    }

    // 유저 삭제 전에 사용. 사진이 없으면 아무것도 안 함 (404를 내지 않음)
    fun deleteProfileImageIfExists(userId: Long) {
        val profileImage = profileImageRepository.findByUserId(userId) ?: return
        profileImageRepository.delete(profileImage)
        Files.deleteIfExists(uploadDir.resolve(profileImage.storedName))
    }
}