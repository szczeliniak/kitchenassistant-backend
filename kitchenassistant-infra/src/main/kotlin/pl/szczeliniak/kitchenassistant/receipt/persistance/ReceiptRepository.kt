package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class ReceiptRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(criteria: SearchCriteria): MutableList<ReceiptEntity> {
        var query = "SELECT r FROM ReceiptEntity r WHERE r.deleted = false"
        if (criteria.userId != null) {
            query += " AND r.userId = :userId"
        }
        if (criteria.categoryId != null) {
            query += " AND r.category.id = :categoryId"
        }
        if (criteria.name != null) {
            query += " AND r.name LIKE :name"
        }

        var typedQuery = entityManager.createQuery(query, ReceiptEntity::class.java)
        if (criteria.userId != null) {
            typedQuery = typedQuery.setParameter("userId", criteria.userId)
        }
        if (criteria.categoryId != null) {
            typedQuery = typedQuery.setParameter("categoryId", criteria.categoryId)
        }
        if (criteria.name != null) {
            typedQuery = typedQuery.setParameter("name", "%" + criteria.name + "%")
        }

        return typedQuery.resultList
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

    data class SearchCriteria(val userId: Int?, val categoryId: Int?, val name: String?)

}