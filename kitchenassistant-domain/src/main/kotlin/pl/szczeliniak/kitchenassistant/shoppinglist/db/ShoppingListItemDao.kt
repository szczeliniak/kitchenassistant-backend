package pl.szczeliniak.kitchenassistant.shoppinglist.db

interface ShoppingListItemDao {

    fun save(shoppingListItem: ShoppingListItem): ShoppingListItem

}