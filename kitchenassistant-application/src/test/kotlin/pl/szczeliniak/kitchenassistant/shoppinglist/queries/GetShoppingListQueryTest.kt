package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDetailsDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListResponse

internal class GetShoppingListQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @Mock
    private lateinit var shoppingListConverter: ShoppingListConverter

    @InjectMocks
    private lateinit var getShoppingListQuery: GetShoppingListQuery

    @Test
    fun shouldReturnShoppingList() {
        val shoppingList = shoppingList()
        val shoppingListDetailsDto = shoppingListDetailsDto()
        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList)
        whenever(shoppingListConverter.mapDetails(shoppingList)).thenReturn(shoppingListDetailsDto)

        val result = getShoppingListQuery.execute(1)

        assertThat(result).isEqualTo(ShoppingListResponse(shoppingListDetailsDto))
    }

    @Test
    fun shouldThrowExceptionWhenShoppingListNotFound() {
        whenever(shoppingListDao.findById(1)).thenReturn(null)

        assertThatThrownBy { getShoppingListQuery.execute(1) }
            .isInstanceOf(KitchenAssistantException::class.java)
            .hasMessage("Shopping list not found")
    }

    private fun shoppingListDetailsDto(): ShoppingListDetailsDto {
        return ShoppingListDetailsDto(0, "", null, null, mutableSetOf(), false)
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            userId = 0,
            name = ""
        )
    }

}