package pl.szczeliniak.kitchenassistant.shoppinglist.commands.factories

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shoppinglist.commands.dto.NewShoppingListItemDto
import pl.szczeliniak.kitchenassistant.shoppinglist.db.ShoppingListItem
import pl.szczeliniak.kitchenassistant.user.db.User

internal class ShoppingListItemFactoryTest : JunitBaseClass() {

    @Mock
    private lateinit var recipeDao: RecipeDao

    @InjectMocks
    private lateinit var shoppingListItemFactory: ShoppingListItemFactory

    @Test
    fun shouldCreateShoppingList() {
        val newShoppingListItemDto = newShoppingListItemDto()
        val recipe = recipe()
        whenever(recipeDao.findById(1)).thenReturn(recipe)

        val result = shoppingListItemFactory.create(newShoppingListItemDto)

        assertThat(result).usingRecursiveComparison()
            .ignoringFields("createdAt", "modifiedAt")
            .isEqualTo(shoppingListItem(recipe))
    }

    private fun recipe(): Recipe {
        return Recipe(id = 1, name = "", User(email = "", name = ""), photoName = null)
    }

    private fun shoppingListItem(recipe: Recipe): ShoppingListItem {
        return ShoppingListItem(
            name = "",
            quantity = null,
            sequence = null,
            recipe = recipe
        )
    }

    private fun newShoppingListItemDto(): NewShoppingListItemDto {
        val dto = NewShoppingListItemDto()
        dto.recipeId = 1
        return dto
    }

}