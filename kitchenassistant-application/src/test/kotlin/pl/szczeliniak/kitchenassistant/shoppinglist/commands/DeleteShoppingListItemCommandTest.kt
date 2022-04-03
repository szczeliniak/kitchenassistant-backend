package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.enums.IngredientUnit
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem

internal class DeleteShoppingListItemCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var deleteShoppingListItemCommand: DeleteShoppingListItemCommand

    @Test
    fun shouldDeleteShoppingListItem() {
        val shoppingListItem = shoppingListItem()
        val shoppingList = shoppingList(shoppingListItem)

        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)
        whenever(shoppingListDao.save(shoppingList)).thenReturn(shoppingList)

        val result = deleteShoppingListItemCommand.execute(1, 2)

        assertThat(shoppingListItem.deleted).isTrue
        assertThat(result).isEqualTo(SuccessResponse(2))
    }

    private fun shoppingList(shoppingListItem: ShoppingListItem): ShoppingList {
        return ShoppingList(
            id_ = 1,
            userId_ = 0,
            name_ = "",
            description_ = "",
            items_ = mutableListOf(shoppingListItem)
        )
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(
            id_ = 2,
            name_ = "",
            quantity_ = "",
            unit_ = IngredientUnit.PINCH_OF,
            0
        )
    }

}