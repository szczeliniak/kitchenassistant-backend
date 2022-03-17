package pl.szczeliniak.kitchenassistant.receipt.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class ReceiptRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(): MutableList<ReceiptEntity> {
        return entityManager
            .createQuery("SELECT r FROM ReceiptEntity r WHERE r.deleted = false", ReceiptEntity::class.java)
            .resultList
    }

    fun findById(id: Int): ReceiptEntity? {
        return entityManager
            .createQuery(
                "SELECT r FROM ReceiptEntity r WHERE r.id = :id AND r.deleted = false",
                ReceiptEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    fun save(entity: ReceiptEntity): ReceiptEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    @Transactional
    fun clear() {
        entityManager.createQuery("DELETE FROM ReceiptEntity").executeUpdate()
    }

}