package pl.szczeliniak.kitchenassistant.dayplan.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class StepSnapshotRepository(@PersistenceContext private val entityManager: EntityManager) : StepSnapshotDao {

    @Transactional
    override fun save(step: StepSnapshot): StepSnapshot {
        entityManager.persist(step)
        return step
    }

}