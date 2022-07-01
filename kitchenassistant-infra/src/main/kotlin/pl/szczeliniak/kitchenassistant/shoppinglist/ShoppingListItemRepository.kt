package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional

@Repository
class ShoppingListItemRepository(@PersistenceContext private val entityManager: EntityManager) : ShoppingListItemDao {

    @Transactional
    override fun save(shoppingListItem: ShoppingListItem): ShoppingListItem {
        if (shoppingListItem.id == 0) {
            entityManager.persist(shoppingListItem)
        } else {
            entityManager.merge(shoppingListItem)
        }
        return shoppingListItem
    }

}