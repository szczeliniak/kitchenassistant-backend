package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse
import java.time.LocalDateTime
import java.util.*

internal class GetShoppingListsQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var getShoppingListsQuery: GetShoppingListsQuery

    @Test
    fun shouldReturnShoppingLists() {
        val createdAt = LocalDateTime.now()
        val modifiedAt = LocalDateTime.now()
        val criteria = ShoppingListCriteria(1)

        whenever(shoppingListDao.findAll(criteria)).thenReturn(
            Collections.singletonList(
                shoppingList(
                    createdAt,
                    modifiedAt
                )
            )
        )

        val result = getShoppingListsQuery.execute(criteria)

        assertThat(result).isEqualTo(
            ShoppingListsResponse(
                Collections.singletonList(
                    shoppingListDto(
                        createdAt,
                        modifiedAt
                    )
                )
            )
        )
    }

    private fun shoppingListDto(createdAt: LocalDateTime, modifiedAt: LocalDateTime): ShoppingListDto {
        return ShoppingListDto(0, 0, "", "", mutableListOf(), createdAt, modifiedAt)
    }

    private fun shoppingList(createdAt: LocalDateTime, modifiedAt: LocalDateTime): ShoppingList {
        return ShoppingList(
            userId_ = 0,
            title_ = "",
            description_ = "",
            createdAt_ = createdAt,
            modifiedAt_ = modifiedAt
        )
    }

}