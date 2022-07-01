package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItemDao

internal class DeleteShoppingListItemCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @Mock
    private lateinit var shoppingListItemDao: ShoppingListItemDao

    @InjectMocks
    private lateinit var deleteShoppingListItemCommand: DeleteShoppingListItemCommand

    @Test
    fun shouldDeleteShoppingListItem() {
        val shoppingListItem = shoppingListItem()
        val shoppingList = shoppingList(shoppingListItem)

        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)
        whenever(shoppingListItemDao.save(shoppingListItem)).thenReturn(shoppingListItem)

        val result = deleteShoppingListItemCommand.execute(1, 2)

        assertThat(shoppingListItem.deleted).isTrue
        assertThat(result).isEqualTo(SuccessResponse(1))
    }

    private fun shoppingList(shoppingListItem: ShoppingListItem): ShoppingList {
        return ShoppingList(
            id = 1,
            userId = 0,
            name = "",
            items = mutableSetOf(shoppingListItem)
        )
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(
            id = 2,
            name = "",
            quantity = "",
            0
        )
    }

}