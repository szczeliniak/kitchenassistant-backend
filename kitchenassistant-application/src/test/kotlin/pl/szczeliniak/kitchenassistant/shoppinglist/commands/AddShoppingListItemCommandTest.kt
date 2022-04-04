package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListItemFactory
import java.util.*

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
        val newShoppingListItemDto = NewShoppingListItemDto()
        val shoppingListItem = shoppingListItem()
        val shoppingList = shoppingList()

        whenever(shoppingListItemFactory.create(newShoppingListItemDto)).thenReturn(shoppingListItem)
        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)
        whenever(shoppingListItemDao.save(shoppingListItem)).thenReturn(shoppingListItem)
        whenever(shoppingListDao.save(shoppingList)).thenReturn(shoppingList)

        val result = addShoppingListItemCommand.execute(1, newShoppingListItemDto)

        assertThat(shoppingList.items).isEqualTo(Collections.singletonList(shoppingListItem))
        assertThat(result).isEqualTo(SuccessResponse(2))
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(
            id_ = 2,
            name_ = "",
            quantity_ = "",
            sequence_ = 0
        )
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            id_ = 1,
            userId_ = 0,
            name_ = ""
        )
    }

}