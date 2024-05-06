package pl.szczeliniak.cookbook.dayplan.db

import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class StepSnapshotRepository(@PersistenceContext private val entityManager: EntityManager) : StepSnapshotDao {

    @Transactional
    override fun save(step: StepSnapshot): StepSnapshot {
        if(step.id > 0) {
            step.modifiedAt = ZonedDateTime.now()
        }
        entityManager.persist(step)
        return step
    }

}