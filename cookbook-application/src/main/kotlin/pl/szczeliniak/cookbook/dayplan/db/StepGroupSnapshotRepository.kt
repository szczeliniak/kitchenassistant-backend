package pl.szczeliniak.cookbook.dayplan.db

import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class StepGroupSnapshotRepository(@PersistenceContext private val entityManager: EntityManager) : StepGroupSnapshotDao {

    @Transactional
    override fun save(stepGroupSnapshot: StepGroupSnapshot): StepGroupSnapshot {
        if(stepGroupSnapshot.id > 0) {
            stepGroupSnapshot.modifiedAt = ZonedDateTime.now()
        }
        entityManager.persist(stepGroupSnapshot)
        return stepGroupSnapshot
    }

}