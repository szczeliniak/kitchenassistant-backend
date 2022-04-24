package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class CategoryRepository(@PersistenceContext private val entityManager: EntityManager) {

    @Transactional
    fun save(entity: CategoryEntity): CategoryEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    fun findAll(criteria: SearchCriteria): MutableSet<CategoryEntity> {
        var query = "SELECT r FROM CategoryEntity r WHERE r.deleted = false"
        if (criteria.userId != null) {
            query += " AND r.userId = :userId"
        }

        var typedQuery = entityManager.createQuery(query, CategoryEntity::class.java)
        if (criteria.userId != null) {
            typedQuery = typedQuery.setParameter("userId", criteria.userId)
        }

        return typedQuery.resultList.toMutableSet()
    }

    fun findById(id: Int): CategoryEntity? {
        return entityManager
            .createQuery(
                "SELECT r FROM CategoryEntity r WHERE r.id = :id AND r.deleted = false",
                CategoryEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    fun clear() {
        entityManager.createQuery("DELETE FROM CategoryEntity").executeUpdate()
    }

    data class SearchCriteria(val userId: Int?)

}