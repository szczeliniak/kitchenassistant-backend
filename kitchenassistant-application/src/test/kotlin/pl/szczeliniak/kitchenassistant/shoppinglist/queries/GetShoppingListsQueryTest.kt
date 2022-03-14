package pl.szczeliniak.kitchenassistant.shoppinglist.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.enums.IngredientUnit
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListCriteria
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListDao
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.queries.dto.ShoppingListItemDto
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
        val shoppingListCreatedAt = LocalDateTime.now()
        val shoppingListModifiedAt = LocalDateTime.now()
        val shoppingListItemCreatedAt = LocalDateTime.now()
        val shoppingListItemModifiedAt = LocalDateTime.now()
        val criteria = ShoppingListCriteria(1)

        whenever(shoppingListDao.findAll(criteria)).thenReturn(
            Collections.singletonList(
                shoppingList(
                    shoppingListItem(shoppingListItemCreatedAt, shoppingListItemModifiedAt),
                    shoppingListCreatedAt,
                    shoppingListModifiedAt
                )
            )
        )

        val result = getShoppingListsQuery.execute(criteria)

        assertThat(result).isEqualTo(
            ShoppingListsResponse(
                Collections.singletonList(
                    shoppingListDto(
                        shoppingListItemDto(shoppingListItemCreatedAt, shoppingListItemModifiedAt),
                        shoppingListCreatedAt,
                        shoppingListModifiedAt
                    )
                )
            )
        )
    }

    private fun shoppingListItemDto(createdAt: LocalDateTime, modifiedAt: LocalDateTime): ShoppingListItemDto {
        return ShoppingListItemDto(2, "NAME", "QUANTITY", IngredientUnit.PINCH_OF, 0, createdAt, modifiedAt)
    }

    private fun shoppingListItem(createdAt: LocalDateTime, modifiedAt: LocalDateTime): ShoppingListItem {
        return ShoppingListItem(
            2,
            "NAME",
            "QUANTITY",
            IngredientUnit.PINCH_OF,
            0,
            createdAt_ = createdAt,
            modifiedAt_ = modifiedAt
        )
    }

    private fun shoppingListDto(
        shoppingListItemDto: ShoppingListItemDto,
        createdAt: LocalDateTime,
        modifiedAt: LocalDateTime
    ): ShoppingListDto {
        return ShoppingListDto(0, 1, "TITLE", "DESCRIPTION", mutableListOf(shoppingListItemDto), createdAt, modifiedAt)
    }

    private fun shoppingList(
        shoppingListItem: ShoppingListItem,
        createdAt: LocalDateTime,
        modifiedAt: LocalDateTime
    ): ShoppingList {
        return ShoppingList(
            userId_ = 1,
            title_ = "TITLE",
            description_ = "DESCRIPTION",
            createdAt_ = createdAt,
            modifiedAt_ = modifiedAt,
            items_ = mutableListOf(shoppingListItem)
        )
    }

}