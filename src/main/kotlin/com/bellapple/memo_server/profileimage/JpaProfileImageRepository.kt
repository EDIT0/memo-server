package com.bellapple.memo_server.profileimage

import com.bellapple.memo_server.profileimage.table.ProfileImage
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class JpaProfileImageRepository(
    private val em: EntityManager
) : ProfileImageRepository {
    override fun save(profileImage: ProfileImage): ProfileImage {
        em.persist(profileImage)
        return profileImage
    }

    override fun findByUserId(userId: Long): ProfileImage? {
        return em.createQuery("select p from ProfileImage p where p.user.id = :userId", ProfileImage::class.java)
            .setParameter("userId", userId)
            .resultList
            .firstOrNull()
    }

    override fun update(profileImage: ProfileImage): ProfileImage {
        em.flush()
        return profileImage
    }

    override fun delete(profileImage: ProfileImage) {
        em.remove(profileImage)
    }

}