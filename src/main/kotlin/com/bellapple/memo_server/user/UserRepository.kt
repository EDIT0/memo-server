package com.bellapple.memo_server.user

// 무엇을 할 수 있는지만 정의. Service는 이 interface에만 의존
interface UserRepository {
    fun save(user: User): User
    fun findById(id: Long): User?
    fun findAll(): List<User>
    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun update(user: User): User
    fun delete(user: User)
}
