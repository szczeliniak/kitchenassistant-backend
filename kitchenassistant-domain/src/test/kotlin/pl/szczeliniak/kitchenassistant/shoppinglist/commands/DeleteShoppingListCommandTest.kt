package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.user.db.User

internal class DeleteShoppingListCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var deleteShoppingListCommand: DeleteShoppingListCommand

    @Test
    fun shouldDeleteShoppingList() {
        val shoppingList = shoppingList()

        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)

        val result = deleteShoppingListCommand.execute(1)

        Mockito.verify(shoppingListDao).delete(shoppingList)
        assertThat(result).isEqualTo(SuccessResponse(1))
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            id = 1,
            user = User(id = 0, email = "", name = ""),
            name = ""
        )
    }

}