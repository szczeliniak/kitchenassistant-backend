package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItemDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListItemDto

internal class UpdateShoppingListItemCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @Mock
    private lateinit var shoppingListItemDao: ShoppingListItemDao

    @InjectMocks
    private lateinit var updateShoppingListItemCommand: UpdateShoppingListItemCommand

    @Test
    fun shouldUpdateShoppingList() {
        val shoppingListItem = shoppingListItem()
        val shoppingList = shoppingList(mutableSetOf(shoppingListItem))

        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)
        whenever(shoppingListItemDao.save(shoppingListItem)).thenReturn(shoppingListItem)

        val result = updateShoppingListItemCommand.execute(1, 2, updateShoppingListItemDto())

        assertThat(result).isEqualTo(SuccessResponse(2))
        assertThat(shoppingListItem.name).isEqualTo("NAME")
        assertThat(shoppingListItem.quantity).isEqualTo("QUANTITY")
        assertThat(shoppingListItem.sequence).isEqualTo(1)
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(2, "", "")
    }

    private fun updateShoppingListItemDto(): UpdateShoppingListItemDto {
        return UpdateShoppingListItemDto("NAME", "QUANTITY", 1)
    }

    private fun shoppingList(items: MutableSet<ShoppingListItem>): ShoppingList {
        return ShoppingList(
            id = 1,
            userId = 0,
            name = "",
            items = items
        )
    }

}