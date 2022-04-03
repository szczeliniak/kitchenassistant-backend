package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto

internal class ShoppingListItemFactoryTest : JunitBaseClass() {

    @InjectMocks
    private lateinit var shoppingListItemFactory: ShoppingListItemFactory

    @Test
    fun shouldCreateShoppingList() {
        val newShoppingListItemDto = NewShoppingListItemDto()

        val result = shoppingListItemFactory.create(newShoppingListItemDto)

        assertThat(result).usingRecursiveComparison()
            .ignoringFields("createdAt_", "modifiedAt_")
            .isEqualTo(shoppingListItem())
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(name_ = "", quantity_ = "", sequence_ = null)
    }

}