package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.TypedQuery
import javax.transaction.Transactional

@Repository
class ShoppingListRepository(@PersistenceContext private val entityManager: EntityManager) : ShoppingListDao {

    override fun findAll(criteria: ShoppingListCriteria, offset: Int?, limit: Int?): Set<ShoppingList> {
        val query =
            "SELECT sl FROM ShoppingList sl" + prepareJoin(criteria) + " WHERE sl.deleted = false" + prepareCriteria(
                criteria
            )
        val typedQuery = applyParameters(criteria, entityManager.createQuery(query, ShoppingList::class.java))
        offset?.let { typedQuery.firstResult = it }
        limit?.let { typedQuery.maxResults = it }
        return typedQuery.resultList.toSet()
    }

    private fun prepareJoin(criteria: ShoppingListCriteria): String {
        val builder = StringBuilder().append("")
        criteria.recipeId?.let { builder.append(" JOIN sl.items i") }
        return builder.toString()
    }

    override fun count(criteria: ShoppingListCriteria): Long {
        val query =
            "SELECT COUNT(sl) FROM ShoppingList sl" + prepareJoin(criteria) + " WHERE sl.deleted = false" + prepareCriteria(
                criteria
            )
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

    @Transactional
    override fun save(shoppingLists: Set<ShoppingList>) {
        shoppingLists.forEach { save(it) }
    }

    private fun prepareCriteria(criteria: ShoppingListCriteria): String {
        val builder = StringBuilder().append("")
        criteria.userId?.let { builder.append(" AND sl.userId = :userId") }
        criteria.archived?.let { builder.append(" AND sl.archived = :archived") }
        criteria.name?.let { builder.append(" AND LOWER(sl.name) LIKE LOWER(:name)") }
        criteria.date?.let { builder.append(" AND sl.date = :date") }
        criteria.recipeId?.let { builder.append(" AND i.recipeId = :recipeId") }
        criteria.automaticArchiving?.let { builder.append(" AND sl.automaticArchiving = :automaticArchiving") }
        criteria.maxDate?.let { builder.append(" AND sl.date IS NOT NULL AND sl.date <= :maxDate") }
        return builder.toString()
    }

    private fun <T> applyParameters(
        criteria: ShoppingListCriteria,
        typedQuery: TypedQuery<T>
    ): TypedQuery<T> {
        var query = typedQuery
        criteria.userId?.let { query = typedQuery.setParameter("userId", it) }
        criteria.archived?.let { query = typedQuery.setParameter("archived", it) }
        criteria.date?.let { query = typedQuery.setParameter("date", it) }
        criteria.name?.let { query = typedQuery.setParameter("name", "%$it%") }
        criteria.recipeId?.let { query = typedQuery.setParameter("recipeId", it) }
        criteria.automaticArchiving?.let { query = typedQuery.setParameter("automaticArchiving", it) }
        criteria.maxDate?.let { query = typedQuery.setParameter("maxDate", it) }
        return query
    }

}