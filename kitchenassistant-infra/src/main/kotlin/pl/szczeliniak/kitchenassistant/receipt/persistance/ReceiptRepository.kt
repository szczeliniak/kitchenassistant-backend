package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class ReceiptRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(criteria: SearchCriteria, offset: Int, limit: Int): MutableList<ReceiptEntity> {
        val query = "SELECT r FROM ReceiptEntity r WHERE r.deleted = false" + prepareCriteria(criteria)
        val typedQuery = applyParameters(criteria, entityManager.createQuery(query, ReceiptEntity::class.java))
        typedQuery.firstResult = offset
        typedQuery.maxResults = limit
        return typedQuery.resultList
    }

    fun count(criteria: SearchCriteria): Long {
        val query = "SELECT COUNT(r) FROM ReceiptEntity r WHERE r.deleted = false" + prepareCriteria(criteria)
        return applyParameters(criteria, entityManager.createQuery(query, Long::class.javaObjectType)).singleResult
    }

    fun findById(id: Int): ReceiptEntity? {
        return entityManager
            .createQuery(
                "SELECT r FROM ReceiptEntity r WHERE r.id = :id AND r.deleted = false",
                ReceiptEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    fun save(entity: ReceiptEntity): ReceiptEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    @Transactional
    fun clear() {
        entityManager.createQuery("DELETE FROM ReceiptEntity").executeUpdate()
    }

    private fun prepareCriteria(criteria: SearchCriteria): String {
        val builder = StringBuilder().append("")
        if (criteria.userId != null) {
            builder.append(" AND r.userId = :userId")
        }
        if (criteria.categoryId != null) {
            builder.append(" AND r.category.id = :categoryId")
        }
        if (criteria.name != null) {
            builder.append(" AND LOWER(r.name) LIKE LOWER(:name)")
        }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: SearchCriteria,
        typedQuery: TypedQuery<T>
    ): TypedQuery<T> {
        var query = typedQuery
        if (criteria.userId != null) {
            query = typedQuery.setParameter("userId", criteria.userId)
        }
        if (criteria.categoryId != null) {
            query = typedQuery.setParameter("categoryId", criteria.categoryId)
        }
        if (criteria.name != null) {
            query = typedQuery.setParameter("name", "%" + criteria.name + "%")
        }
        return query
    }

    data class SearchCriteria(val userId: Int?, val categoryId: Int?, val name: String?)

}