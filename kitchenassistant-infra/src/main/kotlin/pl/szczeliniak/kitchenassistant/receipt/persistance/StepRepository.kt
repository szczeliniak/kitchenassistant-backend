package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class StepRepository(@PersistenceContext private val entityManager: EntityManager) {

    @Transactional
    fun save(entity: StepEntity): StepEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

}