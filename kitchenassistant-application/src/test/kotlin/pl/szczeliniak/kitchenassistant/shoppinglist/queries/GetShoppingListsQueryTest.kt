package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse
import java.util.*

internal class GetShoppingListsQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var getShoppingListsQuery: GetShoppingListsQuery

    @Test
    fun shouldReturnShoppingLists() {
        val criteria = ShoppingListCriteria(1)

        whenever(shoppingListDao.findAll(criteria)).thenReturn(
            Collections.singletonList(
                shoppingList(
                    shoppingListItem()
                )
            )
        )

        val result = getShoppingListsQuery.execute(criteria)

        assertThat(result).isEqualTo(
            ShoppingListsResponse(
                Collections.singletonList(shoppingListDto(shoppingListItemDto()))
            )
        )
    }

    private fun shoppingListItemDto(): ShoppingListItemDto {
        return ShoppingListItemDto(2, "NAME", "QUANTITY", 0, false)
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(
            2,
            "NAME",
            "QUANTITY",
            0
        )
    }

    private fun shoppingListDto(
        shoppingListItemDto: ShoppingListItemDto
    ): ShoppingListDto {
        return ShoppingListDto(0, 1, "NAME", "DESCRIPTION", false, mutableListOf(shoppingListItemDto))
    }

    private fun shoppingList(
        shoppingListItem: ShoppingListItem
    ): ShoppingList {
        return ShoppingList(
            userId_ = 1,
            name_ = "NAME",
            description_ = "DESCRIPTION",
            items_ = mutableListOf(shoppingListItem)
        )
    }

}