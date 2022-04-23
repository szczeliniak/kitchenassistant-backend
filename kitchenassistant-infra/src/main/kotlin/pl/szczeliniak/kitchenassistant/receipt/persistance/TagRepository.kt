package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class TagRepository(@PersistenceContext private val entityManager: EntityManager) {

    @Transactional
    fun save(entity: TagEntity): TagEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    fun findById(id: Int): TagEntity? {
        return entityManager
            .createQuery(
                "SELECT r FROM TagEntity r WHERE r.id = :id AND r.deleted = false",
                TagEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    fun findByName(name: String, userId: Int): TagEntity? {
        return entityManager
            .createQuery(
                "SELECT r FROM TagEntity r WHERE r.name = :name AND r.deleted = false AND r.userId = :userId",
                TagEntity::class.java
            )
            .setParameter("name", name)
            .setParameter("userId", userId)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    fun findAll(criteria: SearchCriteria): List<TagEntity> {
        var query = "SELECT r FROM TagEntity r WHERE r.deleted = false"
        if (criteria.name != null) {
            query += " AND LOWER(r.name) LIKE (:name)"
        }
        if (criteria.userId != null) {
            query += " AND r.userId = :userId"
        }

        var typedQuery = entityManager.createQuery(query, TagEntity::class.java)
        if (criteria.name != null) {
            typedQuery = typedQuery.setParameter("name", "%" + criteria.name + "%")
        }
        if (criteria.userId != null) {
            typedQuery = typedQuery.setParameter("userId", criteria.userId)
        }

        return typedQuery.resultList
    }

    data class SearchCriteria(
        val name: String?,
        val userId: Int?
    )

}