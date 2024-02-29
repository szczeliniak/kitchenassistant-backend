package pl.szczeliniak.kitchenassistant.recipe

import org.mapstruct.Mapper
import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.recipe.db.Tag
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipeResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipesResponse

@Mapper
abstract class RecipeMapper {

    abstract fun map(recipe: Recipe): RecipesResponse.Recipe

    abstract fun map(category: Category?): RecipesResponse.Recipe.Category?

    fun toName(author: Author?): String? {
        return author?.name
    }

    fun toName(tag: Tag?): String? {
        return tag?.name
    }

    abstract fun mapDetails(recipe: Recipe): RecipeResponse.Recipe

    abstract fun mapDetails(category: Category?): RecipeResponse.Recipe.Category?

    abstract fun mapDetails(step: Step?): RecipeResponse.Recipe.Step?

    abstract fun mapDetails(ingredientGroup: IngredientGroup): RecipeResponse.Recipe.IngredientGroup

    abstract fun mapDetails(ingredient: Ingredient): RecipeResponse.Recipe.IngredientGroup.Ingredient

}