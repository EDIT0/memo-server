package com.bellapple.memo_server.profileimage.table

import com.bellapple.memo_server.user.table.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@Entity
@Table(name = "profile_image")
class ProfileImage(
    user: User,
    storedName: String,
    originalName: String,
    contentType: String,
    size: Long
) {
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 알아서 1, 2, 3 증가
    val id: Long? = null

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    val user: User = user

    var storedName: String = storedName
        protected set

    var originalName: String = originalName
        protected set

    var contentType: String = contentType
        protected set

    var size: Long = size
        protected set

    val createdAt: Instant = Instant.now()

    @UpdateTimestamp
    var updatedAt: Instant = Instant.now()
        protected set

    init {
        validate(contentType, size)
    }

    fun update(newStoredName: String, newOriginalName: String, newContentType: String, newSize: Long) {
        validate(newContentType, newSize)
        storedName = newStoredName
        originalName = newOriginalName
        contentType = newContentType
        size = newSize
    }

    private fun validate(contentType: String, size: Long) {
        if(size <= 0) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "파일이 비어 있습니다.")
        }
        if(!contentType.startsWith("image/")) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "이미지 파일만 올릴 수 있습니다.")
        }
    }
}