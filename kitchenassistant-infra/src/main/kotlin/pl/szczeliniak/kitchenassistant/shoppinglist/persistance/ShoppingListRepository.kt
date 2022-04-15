package pl.szczeliniak.kitchenassistant.shoppinglist.persistance

import org.springframework.stereotype.Repository
import java.time.LocalDate
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class ShoppingListRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(criteria: SearchCriteria): MutableList<ShoppingListEntity> {
        var query = "SELECT sl FROM ShoppingListEntity sl WHERE sl.deleted = false"
        if (criteria.userId != null) {
            query += " AND sl.userId = :userId"
        }
        if (criteria.archived != null) {
            query += " AND sl.archived = :archived"
        }
        if (criteria.name != null) {
            query += " AND LOWER(sl.name) LIKE LOWER(:name)"
        }
        if (criteria.date != null) {
            query += " AND sl.date = :date"
        }

        var typedQuery = entityManager.createQuery(query, ShoppingListEntity::class.java)
        if (criteria.userId != null) {
            typedQuery = typedQuery.setParameter("userId", criteria.userId)
        }
        if (criteria.archived != null) {
            typedQuery = typedQuery.setParameter("archived", criteria.archived)
        }
        if (criteria.date != null) {
            typedQuery = typedQuery.setParameter("date", criteria.date)
        }
        if (criteria.name != null) {
            typedQuery = typedQuery.setParameter("name", "%" + criteria.name + "%")
        }

        return typedQuery.resultList
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

    data class SearchCriteria(val userId: Int?, val archived: Boolean?, val name: String?, val date: LocalDate?)

}