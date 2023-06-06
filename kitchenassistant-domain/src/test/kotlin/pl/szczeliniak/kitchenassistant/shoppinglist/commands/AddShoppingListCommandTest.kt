package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListFactory
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.user.db.User

internal class AddShoppingListCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @Mock
    private lateinit var shoppingListFactory: ShoppingListFactory

    @InjectMocks
    private lateinit var addShoppingListCommand: AddShoppingListCommand

    @Test
    fun shouldAddShoppingList() {
        val newShoppingListDto = NewShoppingListDto()
        val shoppingList = shoppingList()

        whenever(shoppingListFactory.create(newShoppingListDto)).thenReturn(shoppingList)
        whenever(shoppingListDao.save(shoppingList)).thenReturn(shoppingList)

        val result = addShoppingListCommand.execute(newShoppingListDto)

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