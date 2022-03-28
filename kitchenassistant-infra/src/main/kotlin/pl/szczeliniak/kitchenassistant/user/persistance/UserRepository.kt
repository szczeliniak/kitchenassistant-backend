package pl.szczeliniak.kitchenassistant.user.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class UserRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(criteria: SearchCriteria): MutableList<UserEntity> {
        var query = "SELECT u FROM UserEntity u"
        if (criteria.email != null) {
            query += " WHERE u.email = :email"
        }

        var typedQuery = entityManager.createQuery(query, UserEntity::class.java)
        if (criteria.email != null) {
            typedQuery = typedQuery.setParameter("email", criteria.email)
        }

        return typedQuery.resultList
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

    data class SearchCriteria(val email: String? = null)

}