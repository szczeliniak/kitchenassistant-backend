package pl.szczeliniak.kitchenassistant.recipe.db

import org.springframework.stereotype.Repository
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroupDao
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class IngredientGroupRepository(@PersistenceContext private val entityManager: EntityManager) : IngredientGroupDao {

    @Transactional
    override fun save(ingredientGroup: IngredientGroup): IngredientGroup {
        if (ingredientGroup.id == 0) {
            entityManager.persist(ingredientGroup)
        } else {
            entityManager.merge(ingredientGroup)
        }
        return ingredientGroup
    }

}