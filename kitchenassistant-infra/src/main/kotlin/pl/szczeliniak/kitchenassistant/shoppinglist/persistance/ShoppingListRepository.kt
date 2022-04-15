package pl.szczeliniak.kitchenassistant.shoppinglist.persistance

import org.springframework.stereotype.Repository
import java.time.LocalDate
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class ShoppingListRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(criteria: SearchCriteria, offset: Int, limit: Int): MutableList<ShoppingListEntity> {
        val query = "SELECT sl FROM ShoppingListEntity sl WHERE sl.deleted = false" + prepareCriteria(criteria)
        val typedQuery = applyParameters(criteria, entityManager.createQuery(query, ShoppingListEntity::class.java))
        typedQuery.firstResult = offset
        typedQuery.maxResults = limit
        return typedQuery.resultList
    }

    fun count(criteria: SearchCriteria): Long {
        val query = "SELECT COUNT(sl) FROM ShoppingListEntity sl WHERE sl.deleted = false" + prepareCriteria(criteria)
        return applyParameters(criteria, entityManager.createQuery(query, Long::class.java)).singleResult
    }

    fun findById(id: Int): ShoppingListEntity? {
        return entityManager
            .createQuery(
                "SELECT sl FROM ShoppingListEntity sl WHERE sl.id = :id AND sl.deleted = false",
                ShoppingListEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    fun save(entity: ShoppingListEntity): ShoppingListEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    @Transactional
    fun clear() {
        entityManager.createQuery("DELETE FROM ShoppingListEntity").executeUpdate()
    }

    private fun prepareCriteria(criteria: SearchCriteria): String {
        val builder = StringBuilder().append("")
        if (criteria.userId != null) {
            builder.append(" AND sl.userId = :userId")
        }
        if (criteria.archived != null) {
            builder.append(" AND sl.archived = :archived")
        }
        if (criteria.name != null) {
            builder.append(" AND LOWER(sl.name) LIKE LOWER(:name)")
        }
        if (criteria.date != null) {
            builder.append(" AND sl.date = :date")
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
        if (criteria.archived != null) {
            query = typedQuery.setParameter("archived", criteria.archived)
        }
        if (criteria.date != null) {
            query = typedQuery.setParameter("date", criteria.date)
        }
        if (criteria.name != null) {
            query = typedQuery.setParameter("name", "%" + criteria.name + "%")
        }
        return query
    }

    data class SearchCriteria(val userId: Int?, val archived: Boolean?, val name: String?, val date: LocalDate?)

}