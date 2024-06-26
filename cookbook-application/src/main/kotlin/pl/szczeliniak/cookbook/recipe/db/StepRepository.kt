package pl.szczeliniak.cookbook.recipe.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class StepRepository(@PersistenceContext private val entityManager: EntityManager) : StepDao {

    @Transactional
    override fun delete(step: Step) {
        entityManager
            .createQuery("DELETE FROM Step s WHERE s.id = :id")
            .setParameter("id", step.id)
            .executeUpdate()
    }

}