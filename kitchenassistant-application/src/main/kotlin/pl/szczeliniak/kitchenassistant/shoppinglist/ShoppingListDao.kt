package pl.szczeliniak.kitchenassistant.shoppinglist

interface ShoppingListDao {

    fun findById(id: Int): ShoppingList?

    fun findAll(criteria: ShoppingListCriteria): List<ShoppingList>

    fun save(shoppingList: ShoppingList): ShoppingList

}