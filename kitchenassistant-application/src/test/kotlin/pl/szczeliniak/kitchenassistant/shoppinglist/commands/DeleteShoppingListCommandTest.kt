package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao

internal class DeleteShoppingListCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var deleteShoppingListCommand: DeleteShoppingListCommand

    @Test
    fun shouldDeleteShoppingList() {
        val shoppingList = shoppingList()

        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)
        whenever(shoppingListDao.save(shoppingList)).thenReturn(shoppingList)

        val result = deleteShoppingListCommand.execute(1)

        assertThat(shoppingList.deleted).isEqualTo(true)
        assertThat(result).isEqualTo(SuccessResponse(1))
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            id_ = 1,
            userId_ = 0,
            name_ = ""
        )
    }

}