package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class ShoppingListRepository(@PersistenceContext private val entityManager: EntityManager) : ShoppingListDao {

    override fun findAll(criteria: ShoppingListCriteria, offset: Int, limit: Int): Set<ShoppingList> {
        val query = "SELECT sl FROM ShoppingList sl WHERE sl.deleted = false" + prepareCriteria(criteria)
        val typedQuery = applyParameters(criteria, entityManager.createQuery(query, ShoppingList::class.java))
        typedQuery.firstResult = offset
        typedQuery.maxResults = limit
        return typedQuery.resultList.toSet()
    }

    override fun count(criteria: ShoppingListCriteria): Long {
        val query = "SELECT COUNT(sl) FROM ShoppingList sl WHERE sl.deleted = false" + prepareCriteria(criteria)
        return applyParameters(criteria, entityManager.createQuery(query, Long::class.javaObjectType)).singleResult
    }

    override fun findById(id: Int): ShoppingList? {
        return entityManager
            .createQuery(
                "SELECT sl FROM ShoppingList sl WHERE sl.id = :id AND sl.deleted = false",
                ShoppingList::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    override fun save(shoppingList: ShoppingList): ShoppingList {
        if (shoppingList.id == 0) {
            entityManager.persist(shoppingList)
        } else {
            entityManager.merge(shoppingList)
        }
        return shoppingList
    }

    private fun prepareCriteria(criteria: ShoppingListCriteria): String {
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
        criteria: ShoppingListCriteria,
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

}