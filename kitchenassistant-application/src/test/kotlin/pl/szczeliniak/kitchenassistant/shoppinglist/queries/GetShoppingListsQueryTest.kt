package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.Pagination
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse
import java.time.LocalDate
import java.time.Month
import java.util.*

internal class GetShoppingListsQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @InjectMocks
    private lateinit var getShoppingListsQuery: GetShoppingListsQuery

    @Test
    fun shouldReturnShoppingLists() {
        val criteria = ShoppingListCriteria(1, false, "NAME", LocalDate.of(2020, Month.APRIL, 1))

        whenever(shoppingListDao.findAll(criteria, 90, 10)).thenReturn(
            Collections.singletonList(
                shoppingList(
                    shoppingListItem()
                )
            )
        )
        whenever(shoppingListDao.count(criteria)).thenReturn(2137L)

        val result = getShoppingListsQuery.execute(10, 10, criteria)

        assertThat(result).isEqualTo(
            ShoppingListsResponse(
                Collections.singletonList(shoppingListDto(shoppingListItemDto())), Pagination(10, 10, 214)
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

    private fun shoppingListDto(shoppingListItemDto: ShoppingListItemDto): ShoppingListDto {
        return ShoppingListDto(0, "NAME", "DESCRIPTION", null, false, mutableListOf(shoppingListItemDto))
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