package pl.szczeliniak.kitchenassistant.shoppinglist

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.shoppinglist.persistance.ShoppingListRepository

@Component
class ShoppingListDaoImpl(
    private val shoppingListRepository: ShoppingListRepository,
    private val shoppingListMapper: ShoppingListMapper
) : ShoppingListDao {

    override fun save(shoppingList: ShoppingList): ShoppingList {
        return shoppingListMapper.toDomain(shoppingListRepository.save(shoppingListMapper.toEntity(shoppingList)))
    }

    override fun findById(id: Int): ShoppingList? {
        return shoppingListRepository.findById(id)
            .map { shoppingListMapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(criteria: ShoppingListCriteria): List<ShoppingList> {
        return shoppingListRepository.findAll() //TODO search by criteria
            .map { shoppingListMapper.toDomain(it) }
    }

}