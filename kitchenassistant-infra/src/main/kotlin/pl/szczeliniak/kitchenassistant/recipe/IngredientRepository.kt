package pl.szczeliniak.kitchenassistant.recipe

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class IngredientRepository(@PersistenceContext private val entityManager: EntityManager) : IngredientDao {

    @Transactional
    override fun save(ingredient: Ingredient): Ingredient {
        if (ingredient.id == 0) {
            entityManager.persist(ingredient)
        } else {
            entityManager.merge(ingredient)
        }
        return ingredient
    }

}