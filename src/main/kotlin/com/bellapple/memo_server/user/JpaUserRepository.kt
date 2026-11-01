package com.bellapple.memo_server.user

import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

// UserRepository의 실제 구현 (EntityManager로 직접 작성)
@Repository
class JpaUserRepository(
    private val em: EntityManager
) : UserRepository {

    override fun save(user: User): User {
        em.persist(user) // INSERT
        return user
    }

    override fun findById(id: Long): User? {
        return em.find(User::class.java, id) // 없으면 null
    }

    override fun findAll(): List<User> {
        return em.createQuery("select u from User u order by u.id", User::class.java)
            .resultList
    }

    // 같은 username이 1개 이상 있으면 true
    override fun existsByUsername(username: String): Boolean {
        return em.createQuery("select count(u) from User u where u.username = :username", Long::class.javaObjectType)
            .setParameter("username", username)
            .singleResult > 0
    }

    override fun existsByEmail(email: String): Boolean {
        return em.createQuery("select count(u) from User u where u.email = :email", Long::class.javaObjectType)
            .setParameter("email", email)
            .singleResult > 0
    }

    override fun update(user: User): User {
        em.flush()
        return user
    }

    override fun delete(user: User) {
        em.remove(user)
    }
}
