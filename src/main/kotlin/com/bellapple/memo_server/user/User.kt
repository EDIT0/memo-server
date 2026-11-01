package com.bellapple.memo_server.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.time.Instant

@Entity
@Table(name = "users") // user는 PostgreSQL 예약어라 테이블 이름을 users로 씀
class User(
    username: String,
    email: String,
    password: String
) {
    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 알아서 1, 2, 3 증가
    val id: Long? = null

    @Column(unique = true) // 같은 username은 한 번만 저장 가능
    var username: String = username
        protected set

    @Column(unique = true) // 같은 email은 한 번만 저장 가능
    var email: String = email
        protected set

    var password: String = password
        protected set

    val createdAt: Instant = Instant.now()

    @UpdateTimestamp // 저장·수정될 때마다 Hibernate가 현재 시각으로 갱신
    var updatedAt: Instant = Instant.now()
        protected set

    init {
        validate(username, email, password)
    }

    fun update(newUsername: String, newEmail: String, newPassword: String) {
        validate(newUsername, newEmail, newPassword)
        username = newUsername
        email = newEmail
        password = newPassword
    }

    // 하나라도 비어 있으면 400 Bad Request로 응답
    private fun validate(username: String, email: String, password: String) {
        if (username.isBlank()) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "username은 비어 있을 수 없습니다.")
        if (email.isBlank()) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "email은 비어 있을 수 없습니다.")
        if (password.isBlank()) throw ResponseStatusException(HttpStatus.BAD_REQUEST, "password는 비어 있을 수 없습니다.")
    }
}
