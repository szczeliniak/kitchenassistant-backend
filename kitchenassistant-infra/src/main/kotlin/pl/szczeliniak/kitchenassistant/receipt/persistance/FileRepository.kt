package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class FileRepository(@PersistenceContext private val entityManager: EntityManager) {

    @Transactional
    fun save(entity: FileEntity): FileEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    fun findById(id: Int): FileEntity? {
        return entityManager
            .createQuery(
                "SELECT r FROM FileEntity r WHERE r.id = :id AND r.deleted = false",
                FileEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

}