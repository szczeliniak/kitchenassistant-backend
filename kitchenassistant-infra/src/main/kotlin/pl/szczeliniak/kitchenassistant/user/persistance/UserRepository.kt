package pl.szczeliniak.kitchenassistant.user.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class UserRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(): MutableList<UserEntity> {
        return entityManager.createQuery("SELECT u FROM UserEntity u", UserEntity::class.java)
            .resultList
    }

    fun findById(id: Int): UserEntity? {
        return entityManager
            .createQuery("SELECT u FROM UserEntity u WHERE u.id = :id", UserEntity::class.java)
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    fun findByEmail(email: String): UserEntity? {
        return entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.email = :email", UserEntity::class.java)
            .setParameter("email", email)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    fun save(entity: UserEntity): UserEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    @Transactional
    fun clear() {
        entityManager.createQuery("DELETE FROM UserEntity").executeUpdate()
    }

}