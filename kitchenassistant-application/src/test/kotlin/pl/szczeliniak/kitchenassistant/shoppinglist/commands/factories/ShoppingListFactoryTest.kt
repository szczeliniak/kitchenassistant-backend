package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingList
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListDto
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import java.util.*

internal class ShoppingListFactoryTest : JunitBaseClass() {

    @Mock
    private lateinit var shoppingListItemFactory: ShoppingListItemFactory

    @InjectMocks
    private lateinit var shoppingListFactory: ShoppingListFactory

    @Test
    fun shouldCreateShoppingList() {
        val newShoppingListItemDto = NewShoppingListItemDto()
        val newShoppingListDto =
            NewShoppingListDto(1, "NAME", "DESCRIPTION", Collections.singletonList(newShoppingListItemDto))
        val shoppingListItem = shoppingListItem()

        whenever(shoppingListItemFactory.create(newShoppingListItemDto)).thenReturn(shoppingListItem)

        val result = shoppingListFactory.create(newShoppingListDto)

        assertThat(result).usingRecursiveComparison()
            .ignoringFields("createdAt_", "modifiedAt_")
            .isEqualTo(shoppingList(mutableListOf(shoppingListItem)))
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(name_ = "", quantity_ = "", sequence_ = null)
    }

    private fun shoppingList(items: MutableList<ShoppingListItem>): ShoppingList {
        return ShoppingList(userId_ = 1, name_ = "NAME", description_ = "DESCRIPTION", items_ = items)
    }

}