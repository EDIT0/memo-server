package com.bellapple.memo_server.memo

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

// MemoRepository의 실제 구현 (EntityManager로 직접 작성)
@Repository
class JpaMemoRepository(
    private val em: EntityManager
) : MemoRepository {

    override fun save(memo: Memo): Memo {
        em.persist(memo) // INSERT. IDENTITY 전략이라 바로 id가 채워짐
        return memo
    }

    override fun findById(id: Long): Memo? {
        return em.find(Memo::class.java, id) // 없으면 null
    }

    // JPQL: 테이블(memo)이 아니라 클래스(Memo) 이름을 씀
    override fun findAll(): List<Memo> {
        return em.createQuery("select m from Memo m order by m.id", Memo::class.java)
            .resultList
    }

    // 특정 유저의 메모만 조회
    override fun findAllByUserId(userId: Long): List<Memo> {
        return em.createQuery("select m from Memo m where m.user.id = :userId order by m.id", Memo::class.java)
            .setParameter("userId", userId)
            .resultList
    }

    // 특정 유저의 메모를 한 번에 삭제 (유저 삭제 전에 사용)
    override fun deleteAllByUserId(userId: Long) {
        em.createQuery("delete from Memo m where m.user.id = :userId")
            .setParameter("userId", userId)
            .executeUpdate()
    }

    // 바뀐 값은 JPA가 이미 추적 중이라, flush로 DB에 바로 반영만 함
    // (이때 @UpdateTimestamp가 updatedAt을 채움)
    override fun update(memo: Memo): Memo {
        em.flush()
        return memo
    }

    override fun delete(memo: Memo) {
        em.remove(memo) // find로 가져온 엔티티만 지울 수 있음
    }
}
