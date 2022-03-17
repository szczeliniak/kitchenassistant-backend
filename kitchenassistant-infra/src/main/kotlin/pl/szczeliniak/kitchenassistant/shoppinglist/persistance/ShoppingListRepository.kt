package pl.szczeliniak.kitchenassistant.shoppinglist.persistance

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class ShoppingListRepository(@PersistenceContext private val entityManager: EntityManager) {

    fun findAll(): MutableList<ShoppingListEntity> {
        return entityManager
            .createQuery(
                "SELECT sl FROM ShoppingListEntity sl WHERE sl.deleted = false",
                ShoppingListEntity::class.java
            )
            .resultList
    }

    fun findById(id: Int): ShoppingListEntity? {
        return entityManager
            .createQuery(
                "SELECT sl FROM ShoppingListEntity sl WHERE sl.id = :id AND sl.deleted = false",
                ShoppingListEntity::class.java
            )
            .setParameter("id", id)
            .resultList
            .stream()
            .findFirst()
            .orElse(null)
    }

    @Transactional
    fun save(entity: ShoppingListEntity): ShoppingListEntity {
        if (entity.id == 0) {
            entityManager.persist(entity)
        } else {
            entityManager.merge(entity)
        }
        return entity
    }

    @Transactional
    fun clear() {
        entityManager.createQuery("DELETE FROM ShoppingListEntity").executeUpdate()
    }

}