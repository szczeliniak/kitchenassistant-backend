package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemRequest
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.user.db.User

internal class AddShoppingListItemCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @Mock
    private lateinit var shoppingListItemFactory: ShoppingListItemFactory

    @Mock
    private lateinit var shoppingListItemDao: ShoppingListItemDao

    @InjectMocks
    private lateinit var addShoppingListItemCommand: AddShoppingListItemCommand

    @Test
    fun shouldAddShoppingListItem() {
        val newShoppingListItemRequest = NewShoppingListItemRequest()
        val shoppingListItem = shoppingListItem()
        val shoppingList = shoppingList()

        whenever(shoppingListItemFactory.create(newShoppingListItemRequest)).thenReturn(shoppingListItem)
        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)
        whenever(shoppingListItemDao.save(shoppingListItem)).thenReturn(shoppingListItem)
        whenever(shoppingListDao.save(shoppingList)).thenReturn(shoppingList)

        val result = addShoppingListItemCommand.execute(1, newShoppingListItemRequest)

        assertThat(shoppingList.items).isEqualTo(setOf(shoppingListItem))
        assertThat(result).isEqualTo(SuccessResponse(2))
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(
            id = 2,
            name = "",
            quantity = "",
            sequence = 0
        )
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            id = 1,
            user = User(id = 0, email = "", name = ""),
            name = ""
        )
    }

}