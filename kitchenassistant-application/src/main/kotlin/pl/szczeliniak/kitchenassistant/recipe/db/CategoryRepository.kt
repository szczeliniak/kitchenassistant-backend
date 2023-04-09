package pl.szczeliniak.kitchenassistant.recipe.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class CategoryRepository(@PersistenceContext private val entityManager: EntityManager) : CategoryDao {

    @Transactional
    override fun save(category: Category): Category {
        if (category.id == 0) {
            entityManager.persist(category)
        } else {
            entityManager.merge(category)
        }
        return category
    }

    override fun delete(category: Category) {
        entityManager.remove(category)
    }

    override fun findAll(criteria: CategoryCriteria): MutableSet<Category> {
        var query = "SELECT c FROM Category c"
        if (criteria.userId != null) {
            query += " AND c.userId = :userId"
        }

        query += " ORDER BY c.sequence ASC NULLS LAST, c.id ASC"

        var typedQuery = entityManager.createQuery(query, Category::class.java)
        if (criteria.userId != null) {
            typedQuery = typedQuery.setParameter("userId", criteria.userId)
        }

        return typedQuery.resultList.toMutableSet()
    }

    override fun findById(id: Int): Category? {
        return entityManager
            .createQuery(
                "SELECT r FROM Category r WHERE r.id = :id",
                Category::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

}