package pl.szczeliniak.kitchenassistant.dayplan.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class RecipeSnapshotRepository(@PersistenceContext private val entityManager: EntityManager) : RecipeSnapshotDao {

    @Transactional
    override fun save(recipe: RecipeSnapshot): RecipeSnapshot {
        entityManager.persist(recipe)
        return recipe
    }

}