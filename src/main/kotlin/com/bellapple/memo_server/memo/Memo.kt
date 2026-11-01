package com.bellapple.memo_server.memo

import com.bellapple.memo_server.user.User
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@Entity
class Memo(
    user: User,     // 메모를 쓴 유저
    content: String // val/var 없이 받기만 하고, 아래에서 검증 후 저장
) {
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 알아서 1, 2, 3 증가
    val id: Long? = null

    @ManyToOne // 메모 여러 개 -> 유저 1명
    @JoinColumn(name = "user_id", nullable = false) // memo 테이블에 user_id 컬럼으로 저장
    val user: User = user

    var content: String = content
        protected set // 밖에서는 읽기만 가능. 바꾸려면 updateContent() 사용 (JPA 엔티티는 open이라 private 대신 protected)

    val createdAt: Instant = Instant.now()

    @UpdateTimestamp // 저장·수정될 때마다 Hibernate가 현재 시각으로 갱신
    var updatedAt: Instant = Instant.now()
        protected set

    init {
        validateContent(content)
    }

    fun updateContent(newContent: String) {
        validateContent(newContent)
        content = newContent
    }

    // 비어 있으면 400 Bad Request로 응답
    private fun validateContent(value: String) {
        if (value.isBlank()) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "메모 내용은 비어 있을 수 없습니다.")
    }
}
