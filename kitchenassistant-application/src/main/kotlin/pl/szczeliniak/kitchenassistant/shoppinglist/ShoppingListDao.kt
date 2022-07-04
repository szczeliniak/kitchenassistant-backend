package pl.szczeliniak.kitchenassistant.shoppinglist

interface ShoppingListDao {

    fun findById(id: Int): ShoppingList?

    fun findAll(criteria: ShoppingListCriteria, offset: Int? = null, limit: Int? = null): Set<ShoppingList>

    fun count(criteria: ShoppingListCriteria): Long

    fun save(shoppingList: ShoppingList): ShoppingList

    fun save(shoppingLists: Set<ShoppingList>)

}