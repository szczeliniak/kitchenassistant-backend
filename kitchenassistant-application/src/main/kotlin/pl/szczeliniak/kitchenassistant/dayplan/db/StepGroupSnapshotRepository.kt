package pl.szczeliniak.kitchenassistant.dayplan.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class StepGroupSnapshotRepository(@PersistenceContext private val entityManager: EntityManager) : StepGroupSnapshotDao {

    @Transactional
    override fun save(stepGroupSnapshot: StepGroupSnapshot): StepGroupSnapshot {
        entityManager.persist(stepGroupSnapshot)
        return stepGroupSnapshot
    }

}