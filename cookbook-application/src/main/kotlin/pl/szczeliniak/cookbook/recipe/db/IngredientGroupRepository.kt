package pl.szczeliniak.cookbook.recipe.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class IngredientGroupRepository(@PersistenceContext private val entityManager: EntityManager) : IngredientGroupDao {

    @Transactional
    override fun delete(ingredientGroup: IngredientGroup) {
        entityManager
            .createQuery("DELETE FROM IngredientGroup ig WHERE ig.id = :id")
            .setParameter("id", ingredientGroup.id)
            .executeUpdate()
    }

}