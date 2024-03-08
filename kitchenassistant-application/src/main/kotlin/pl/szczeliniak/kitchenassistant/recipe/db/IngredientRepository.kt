package pl.szczeliniak.kitchenassistant.recipe.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class IngredientRepository(@PersistenceContext private val entityManager: EntityManager) : IngredientDao {

    @Transactional
    override fun delete(ingredient: Ingredient) {
        entityManager
            .createQuery("DELETE FROM Ingredient i WHERE i.id = :id")
            .setParameter("id", ingredient.id)
            .executeUpdate()
    }

}