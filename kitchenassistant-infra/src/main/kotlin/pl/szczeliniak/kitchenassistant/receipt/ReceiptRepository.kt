package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class ReceiptRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(criteria: SearchCriteria, offset: Int, limit: Int): MutableSet<ReceiptEntity> {
        val query =
            "SELECT DISTINCT r FROM ReceiptEntity r " + prepareJoin(criteria) + "WHERE r.deleted = false ORDER BY r.favorite DESC NULLS LAST, r.id ASC" + prepareCriteria(
                criteria
            )
        val typedQuery = applyParameters(criteria, entityManager.createQuery(query, ReceiptEntity::class.java))
        typedQuery.firstResult = offset
        typedQuery.maxResults = limit
        return typedQuery.resultList.toMutableSet()
    }

    private fun prepareJoin(criteria: SearchCriteria): String {
        val builder = StringBuilder()
        if (criteria.tag != null) {
            builder.append("JOIN r.tags t ")
        }
        return builder.toString()
    }

    fun count(criteria: SearchCriteria): Long {
        val query =
            "SELECT DISTINCT COUNT(r) FROM ReceiptEntity r " + prepareJoin(criteria) + "WHERE r.deleted = false" + prepareCriteria(
                criteria
            )
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
        if (criteria.tag != null) {
            builder.append(" AND LOWER(t.name) LIKE LOWER(:tag)")
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
        if (criteria.tag != null) {
            query = typedQuery.setParameter("tag", "%" + criteria.tag + "%")
        }
        return query
    }

    data class SearchCriteria(val userId: Int?, val categoryId: Int?, val name: String?, val tag: String?)

}