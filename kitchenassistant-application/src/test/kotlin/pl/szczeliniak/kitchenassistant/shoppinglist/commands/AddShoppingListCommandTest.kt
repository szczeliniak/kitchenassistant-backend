package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories.ShoppingListFactory

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

        assertThat(result).isEqualTo(SuccessResponse())
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            userId_ = 0,
            title_ = "",
            description_ = ""
        )
    }

}