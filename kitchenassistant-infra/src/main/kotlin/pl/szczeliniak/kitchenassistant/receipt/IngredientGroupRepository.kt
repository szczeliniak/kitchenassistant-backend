package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class IngredientGroupRepository(@PersistenceContext private val entityManager: EntityManager) {

    @Transactional
    fun save(entity: IngredientGroupEntity): IngredientGroupEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

}