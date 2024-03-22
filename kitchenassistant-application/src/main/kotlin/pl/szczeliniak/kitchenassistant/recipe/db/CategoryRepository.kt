package pl.szczeliniak.kitchenassistant.recipe.db

import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
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
            category.modifiedAt = ZonedDateTime.now()
            entityManager.merge(category)
        }
        return category
    }

    @Transactional
    override fun delete(category: Category) {
        entityManager.remove(category)
    }

    override fun findAll(userId: Int): List<Category> {
        val query = "SELECT c FROM Category c WHERE c.user.id = :userId ORDER BY c.id ASC"
        return entityManager.createQuery(query, Category::class.java).setParameter("userId", userId).resultList.toMutableList()
    }

    override fun findById(id: Int, userId: Int): Category? {
        return entityManager
            .createQuery(
                "SELECT c FROM Category c WHERE c.id = :id AND c.user.id = :userId",
                Category::class.java
            )
            .setParameter("id", id)
            .setParameter("userId", userId)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

}