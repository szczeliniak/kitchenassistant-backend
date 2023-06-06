package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.user.db.User

internal class DeleteShoppingListItemCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var deleteShoppingListItemCommand: DeleteShoppingListItemCommand

    @Test
    fun shouldDeleteShoppingListItem() {
        val shoppingList = shoppingList()
        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)

        val result = deleteShoppingListItemCommand.execute(1, 2)

        Mockito.verify(shoppingListDao).save(shoppingList)
        assertThat(shoppingList.items).isEmpty()
        assertThat(result).isEqualTo(SuccessResponse(2))
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            id = 1,
            user = User(id = 0, email = "", name = ""),
            name = "",
            items = mutableSetOf(shoppingListItem())
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