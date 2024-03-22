package pl.szczeliniak.kitchenassistant.dayplan.db

import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class IngredientSnapshotRepository(@PersistenceContext private val entityManager: EntityManager) :
    IngredientSnapshotDao {

    @Transactional
    override fun save(ingredient: IngredientSnapshot): IngredientSnapshot {
        if(ingredient.id > 0) {
            ingredient.modifiedAt = ZonedDateTime.now()
        }
        entityManager.persist(ingredient)
        return ingredient
    }

}