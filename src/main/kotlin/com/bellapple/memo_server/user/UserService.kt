package com.bellapple.memo_server.user

import com.bellapple.memo_server.memo.MemoRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

// 비즈니스 로직을 담당 (HTTP, DTO, DB 구현을 모름)
@Service
@Transactional // EntityManager는 트랜잭션 안에서만 저장·삭제 가능
class UserService(
    private val userRepository: UserRepository,
    private val memoRepository: MemoRepository // 유저 삭제 시 그 유저의 메모도 지우기 위해 사용
) {
    fun createUser(username: String, email: String, password: String): User {
        // 이미 있는 username·email이면 409 Conflict로 응답
        if (userRepository.existsByUsername(username)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 username입니다.")
        }
        if (userRepository.existsByEmail(email)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 email입니다.")
        }
        return userRepository.save(User(username, email, password))
    }

    @Transactional(readOnly = true)
    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getUser(id: Long): User {
        return findUser(id)
    }

    fun updateUser(id: Long, username: String, email: String, password: String): User {
        val user = findUser(id)
        // 바꾸려는 값이 다른 유저가 이미 쓰고 있으면 409
        if (username != user.username && userRepository.existsByUsername(username)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 username입니다.")
        }
        if (email != user.email && userRepository.existsByEmail(email)) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "이미 사용 중인 email입니다.")
        }
        user.update(username, email, password)
        return userRepository.update(user)
    }

    fun deleteUser(id: Long) {
        val user = findUser(id)
        memoRepository.deleteAllByUserId(id) // 메모가 유저를 참조하고 있어서 메모를 먼저 지워야 함
        userRepository.delete(user)
    }

    private fun findUser(id: Long): User {
        // 없으면 404 Not Found로 응답
        return userRepository.findById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. id=$id")
    }
}
