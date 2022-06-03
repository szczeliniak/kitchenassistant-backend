package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class AuthorRepository(@PersistenceContext private val entityManager: EntityManager) {

    @Transactional
    fun save(entity: AuthorEntity): AuthorEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    fun findById(id: Int): AuthorEntity? {
        return entityManager
            .createQuery(
                "SELECT r FROM AuthorEntity r WHERE r.id = :id",
                AuthorEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    fun findByName(name: String, userId: Int): AuthorEntity? {
        return entityManager
            .createQuery(
                "SELECT r FROM AuthorEntity r WHERE r.name = :name AND r.userId = :userId",
                AuthorEntity::class.java
            )
            .setParameter("name", name)
            .setParameter("userId", userId)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    fun findAll(criteria: SearchCriteria): Set<AuthorEntity> {
        var query = "SELECT r FROM AuthorEntity r WHERE r.id IS NOT NULL"
        if (criteria.name != null) {
            query += " AND LOWER(r.name) LIKE (:name)"
        }
        if (criteria.userId != null) {
            query += " AND r.userId = :userId"
        }

        var typedQuery = entityManager.createQuery(query, AuthorEntity::class.java)
        if (criteria.name != null) {
            typedQuery = typedQuery.setParameter("name", "%" + criteria.name + "%")
        }
        if (criteria.userId != null) {
            typedQuery = typedQuery.setParameter("userId", criteria.userId)
        }

        return typedQuery.resultList.toMutableSet()
    }

    data class SearchCriteria(
        val name: String?,
        val userId: Int?
    )

}