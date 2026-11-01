package com.bellapple.memo_server.memo

import com.bellapple.memo_server.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

// 비즈니스 로직을 담당 (HTTP, DTO, DB 구현을 모름)
@Service
@Transactional // EntityManager는 트랜잭션 안에서만 저장·삭제 가능
class MemoService(
    private val memoRepository: MemoRepository, // interface에 의존
    private val userService: UserService        // 메모 주인(유저)을 찾기 위해 사용
) {
    fun createMemo(userId: Long, content: String): Memo {
        val user = userService.getUser(userId) // 없는 유저면 404
        return memoRepository.save(Memo(user, content))
    }

    // 특정 유저의 메모 목록
    @Transactional(readOnly = true)
    fun getAllMemos(userId: Long): List<Memo> {
        userService.getUser(userId) // 없는 유저면 404
        return memoRepository.findAllByUserId(userId)
    }

    @Transactional(readOnly = true)
    fun getMemo(id: Long): Memo {
        return findMemo(id)
    }

    fun updateMemo(id: Long, content: String): Memo {
        val memo = findMemo(id)
        memo.updateContent(content)
        return memoRepository.update(memo)
    }

    fun deleteMemo(id: Long) {
        memoRepository.delete(findMemo(id))
    }

    private fun findMemo(id: Long): Memo {
        // 없으면 404 Not Found로 응답
        return memoRepository.findById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "메모를 찾을 수 없습니다. id=$id")
    }
}
