package pl.szczeliniak.kitchenassistant.dayplan.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class IngredientSnapshotRepository(@PersistenceContext private val entityManager: EntityManager) :
    IngredientSnapshotDao {

    @Transactional
    override fun save(ingredient: IngredientSnapshot): IngredientSnapshot {
        entityManager.persist(ingredient)
        return ingredient
    }

}