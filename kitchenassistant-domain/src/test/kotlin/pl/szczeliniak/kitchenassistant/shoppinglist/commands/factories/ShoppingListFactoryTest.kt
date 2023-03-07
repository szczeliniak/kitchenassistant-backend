package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import java.time.LocalDate

internal class ShoppingListFactoryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListItemFactory: ShoppingListItemFactory

    @InjectMocks
    private lateinit var shoppingListFactory: ShoppingListFactory

    @Test
    fun shouldCreateShoppingList() {
        val date = LocalDate.now()
        val newShoppingListItemDto = NewShoppingListItemDto()
        val newShoppingListDto =
            NewShoppingListDto(1, "NAME", "DESCRIPTION", date, mutableSetOf(newShoppingListItemDto))
        val shoppingListItem = shoppingListItem()

        whenever(shoppingListItemFactory.create(newShoppingListItemDto)).thenReturn(shoppingListItem)

        val result = shoppingListFactory.create(newShoppingListDto)

        assertThat(result).usingRecursiveComparison()
            .ignoringFields("createdAt", "modifiedAt")
            .isEqualTo(shoppingList(date, mutableSetOf(shoppingListItem)))
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(name = "", quantity = "", sequence = null)
    }

    private fun shoppingList(date: LocalDate, items: MutableSet<ShoppingListItem>): ShoppingList {
        return ShoppingList(userId = 1, name = "NAME", description = "DESCRIPTION", date = date, items = items)
    }

}