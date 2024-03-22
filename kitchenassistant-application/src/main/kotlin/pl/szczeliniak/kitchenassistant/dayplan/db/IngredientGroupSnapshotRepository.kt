package pl.szczeliniak.kitchenassistant.dayplan.db

import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class IngredientGroupSnapshotRepository(@PersistenceContext private val entityManager: EntityManager) :
    IngredientGroupSnapshotDao {

    @Transactional
    override fun save(ingredientGroup: IngredientGroupSnapshot): IngredientGroupSnapshot {
        if(ingredientGroup.id > 0) {
            ingredientGroup.modifiedAt = ZonedDateTime.now()
        }
        entityManager.persist(ingredientGroup)
        return ingredientGroup
    }

}