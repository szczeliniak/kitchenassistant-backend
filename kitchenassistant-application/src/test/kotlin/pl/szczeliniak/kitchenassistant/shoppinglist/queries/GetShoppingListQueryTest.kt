package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse

internal class GetShoppingListQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var getShoppingListQuery: GetShoppingListQuery

    @Test
    fun shouldReturnShoppingList() {
        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList())

        val result = getShoppingListQuery.execute(1)

        assertThat(result).isEqualTo(ShoppingListResponse(shoppingListDto()))
    }

    @Test
    fun shouldThrowExceptionWhenShoppingListNotFound() {
        whenever(shoppingListDao.findById(1)).thenReturn(null)

        assertThatThrownBy { getShoppingListQuery.execute(1) }
            .isInstanceOf(NotFoundException::class.java)
            .hasMessage("Shopping list not found")
    }

    private fun shoppingListDto(): ShoppingListDto {
        return ShoppingListDto(0, 0, "", "", false, mutableListOf())
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            userId_ = 0,
            name_ = "",
            description_ = ""
        )
    }

}