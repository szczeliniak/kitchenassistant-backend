package pl.szczeliniak.kitchenassistant.shoppinglist

interface ShoppingListItemDao {

    fun save(shoppingListItem: ShoppingListItem): ShoppingListItem

}