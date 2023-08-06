package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shared.dtos.Pagination
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListsResponse
import pl.szczeliniak.kitchenassistant.user.db.User
import java.time.LocalDate
import java.time.Month
import java.util.*

internal class GetShoppingListsQueryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListDao: ShoppingListDao

    @Mock
    private lateinit var shoppingListConverter: ShoppingListConverter

    @InjectMocks
    private lateinit var getShoppingListsQuery: GetShoppingListsQuery

    @Test
    fun shouldReturnShoppingLists() {
        val criteria = ShoppingListCriteria(1, false, "NAME", LocalDate.of(2020, Month.APRIL, 1))
        val shoppingList = shoppingList()
        val shoppingListDto = shoppingListDto()
        whenever(shoppingListDao.findAll(criteria, 90, 10)).thenReturn(mutableSetOf(shoppingList))
        whenever(shoppingListDao.count(criteria)).thenReturn(2137L)
        whenever(shoppingListConverter.map(shoppingList)).thenReturn(shoppingListDto)

        val result = getShoppingListsQuery.execute(10, 10, criteria)

        assertThat(result).isEqualTo(ShoppingListsResponse(mutableSetOf(shoppingListDto), Pagination(10, 10, 214)))
    }

    private fun shoppingListDto(): ShoppingListDto {
        return ShoppingListDto(0, "", "", null)
    }

    private fun shoppingList(): ShoppingList {
        return ShoppingList(
            user = User(id = 1, email = ""),
            name = "",
            description = "",
            items = Collections.emptySet()
        )
    }

}