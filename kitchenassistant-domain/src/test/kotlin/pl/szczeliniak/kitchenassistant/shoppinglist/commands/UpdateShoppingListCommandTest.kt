package pl.szczeliniak.kitchenassistant.shoppinglist.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.UpdateShoppingListDto
import java.time.LocalDate

internal class UpdateShoppingListCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var updateShoppingListCommand: UpdateShoppingListCommand

    @Test
    fun shouldUpdateShoppingList() {
        val shoppingList = shoppingList()

        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)
        whenever(shoppingListDao.save(shoppingList)).thenReturn(shoppingList)

        val result = updateShoppingListCommand.execute(1, updateShoppingListDto())

        assertThat(result).isEqualTo(SuccessResponse(1))
        assertThat(shoppingList.name).isEqualTo("NAME")
        assertThat(shoppingList.description).isEqualTo("DESCRIPTION")
        assertThat(shoppingList.date).isEqualTo(LocalDate.of(2000, 1, 1))
    }

    private fun updateShoppingListDto(): UpdateShoppingListDto {
        return UpdateShoppingListDto("NAME", "DESCRIPTION", LocalDate.of(2000, 1, 1))
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            id = 1,
            userId = 0,
            name = ""
        )
    }

}