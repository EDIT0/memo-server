package com.bellapple.memo_server.memo

// 무엇을 할 수 있는지만 정의. Service는 이 interface에만 의존
interface MemoRepository {
    fun save(memo: Memo): Memo
    fun findById(id: Long): Memo?
    fun findAll(): List<Memo>
    fun findAllByUserId(userId: Long): List<Memo>
    fun deleteAllByUserId(userId: Long)
    fun update(memo: Memo): Memo
    fun delete(memo: Memo)
}
