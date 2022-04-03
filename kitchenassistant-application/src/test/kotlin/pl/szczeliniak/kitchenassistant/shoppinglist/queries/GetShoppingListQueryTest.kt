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
import java.time.LocalDateTime

internal class GetShoppingListQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var getShoppingListQuery: GetShoppingListQuery

    @Test
    fun shouldReturnShoppingList() {
        val createdAt = LocalDateTime.now()
        val modifiedAt = LocalDateTime.now()

        whenever(shoppingListDao.findById(1)).thenReturn(shoppingList(createdAt, modifiedAt))

        val result = getShoppingListQuery.execute(1)

        assertThat(result).isEqualTo(ShoppingListResponse(shoppingListDto(createdAt, modifiedAt)))
    }

    @Test
    fun shouldThrowExceptionWhenShoppingListNotFound() {
        whenever(shoppingListDao.findById(1)).thenReturn(null)

        assertThatThrownBy { getShoppingListQuery.execute(1) }
            .isInstanceOf(NotFoundException::class.java)
            .hasMessage("Shopping list not found")
    }

    private fun shoppingListDto(createdAt: LocalDateTime, modifiedAt: LocalDateTime): ShoppingListDto {
        return ShoppingListDto(0, 0, "", "", mutableListOf(), createdAt, modifiedAt)
    }

    private fun shoppingList(createdAt: LocalDateTime, modifiedAt: LocalDateTime): ShoppingList {
        return ShoppingList(
            userId_ = 0,
            name_ = "",
            description_ = "",
            createdAt_ = createdAt,
            modifiedAt_ = modifiedAt
        )
    }

}