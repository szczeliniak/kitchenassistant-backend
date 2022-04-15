package pl.szczeliniak.kitchenassistant.shoppinglist

interface ShoppingListDao {

    fun findById(id: Int): ShoppingList?

    fun findAll(criteria: ShoppingListCriteria, offset: Int, limit: Int): List<ShoppingList>

    fun count(criteria: ShoppingListCriteria): Long

    fun save(shoppingList: ShoppingList): ShoppingList

}