package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeDetailsDto
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.RecipeResponse
import pl.szczeliniak.kitchenassistant.shoppinglist.ShoppingListItem
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import java.util.*

internal class ShoppingListItemFactoryTest : JunitBaseClass() {

    @Mock
    private lateinit var recipeFacade: RecipeFacade

    @InjectMocks
    private lateinit var shoppingListItemFactory: ShoppingListItemFactory

    @Test
    fun shouldCreateShoppingList() {
        val newShoppingListItemDto = newShoppingListItemDto()
        whenever(recipeFacade.findById(1)).thenReturn(recipeResponse())

        val result = shoppingListItemFactory.create(newShoppingListItemDto)

        assertThat(result).usingRecursiveComparison()
            .ignoringFields("createdAt", "modifiedAt")
            .isEqualTo(shoppingListItem())
    }

    private fun recipeResponse(): RecipeResponse {
        return RecipeResponse(
            RecipeDetailsDto(
                0,
                "",
                null,
                null,
                null,
                null,
                null,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet()
            )
        )
    }

    private fun shoppingListItem(): ShoppingListItem {
        return ShoppingListItem(name = "", quantity = null, sequence = null, recipeId = 1)
    }

    private fun newShoppingListItemDto(): NewShoppingListItemDto {
        val dto = NewShoppingListItemDto()
        dto.recipeId = 1
        return dto
    }

}