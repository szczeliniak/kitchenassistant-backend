package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class PhotoRepository(@PersistenceContext private val entityManager: EntityManager) {

    @Transactional
    fun save(entity: PhotoEntity): PhotoEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    fun findById(id: Int): PhotoEntity? {
        return entityManager
            .createQuery(
                "SELECT r FROM PhotoEntity r WHERE r.id = :id AND r.deleted = false",
                PhotoEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

}