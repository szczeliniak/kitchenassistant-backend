package pl.szczeliniak.kitchenassistant.user.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class UserRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(criteria: SearchCriteria, offset: Int, limit: Int): MutableList<UserEntity> {
        val query = "SELECT u FROM UserEntity u" + prepareCriteria(criteria)
        val typedQuery = applyParameters(criteria, entityManager.createQuery(query, UserEntity::class.java))
        typedQuery.firstResult = offset
        typedQuery.maxResults = limit
        return typedQuery.resultList
    }

    fun count(criteria: SearchCriteria): Long {
        val query = "SELECT COUNT(u) FROM UserEntity u" + prepareCriteria(criteria)
        return applyParameters(criteria, entityManager.createQuery(query, Long::class.javaObjectType)).singleResult
    }

    private fun prepareCriteria(criteria: SearchCriteria): String {
        val builder = StringBuilder().append("")
        if (criteria.email != null) {
            builder.append(" WHERE u.email = :email")
        }
        return builder.toString()
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

    private fun <T> applyParameters(
        criteria: SearchCriteria,
        typedQuery: TypedQuery<T>
    ): TypedQuery<T> {
        var query = typedQuery
        if (criteria.email != null) {
            query = typedQuery.setParameter("email", criteria.email)
        }
        return query
    }

    data class SearchCriteria(val email: String? = null)

}