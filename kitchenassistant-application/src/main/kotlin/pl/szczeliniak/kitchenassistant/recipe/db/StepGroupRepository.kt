package pl.szczeliniak.kitchenassistant.recipe.db

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class StepGroupRepository(@PersistenceContext private val entityManager: EntityManager) : StepGroupDao {

    @Transactional
    override fun delete(stepGroup: StepGroup) {
        entityManager
            .createQuery("DELETE FROM StepGroup ig WHERE ig.id = :id")
            .setParameter("id", stepGroup.id)
            .executeUpdate()
    }

}