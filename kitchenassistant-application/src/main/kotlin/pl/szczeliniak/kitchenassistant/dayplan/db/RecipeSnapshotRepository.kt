package pl.szczeliniak.kitchenassistant.dayplan.db

import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class RecipeSnapshotRepository(@PersistenceContext private val entityManager: EntityManager) : RecipeSnapshotDao {

    @Transactional
    override fun save(recipe: RecipeSnapshot): RecipeSnapshot {
        if(recipe.id > 0) {
            recipe.modifiedAt = ZonedDateTime.now()
        }
        entityManager.persist(recipe)
        return recipe
    }

}