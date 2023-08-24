package pl.szczeliniak.kitchenassistant.recipe.mapper

import org.mapstruct.Mapper
import pl.szczeliniak.kitchenassistant.recipe.db.*
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipeResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipesResponse

@Mapper
abstract class RecipeMapper {

    abstract fun map(recipe: Recipe): RecipesResponse.RecipeDto

    abstract fun map(category: Category?): RecipesResponse.RecipeDto.CategoryDto?

    fun toName(author: Author?): String? {
        return author?.name
    }
    
    fun toName(tag: Tag?): String? {
        return tag?.name
    }

    abstract fun mapDetails(recipe: Recipe): RecipeResponse.RecipeDto

    abstract fun mapDetails(category: Category?): RecipeResponse.RecipeDto.CategoryDto?

    abstract fun mapDetails(step: Step?): RecipeResponse.RecipeDto.StepDto?

    abstract fun mapDetails(ingredientGroup: IngredientGroup): RecipeResponse.RecipeDto.IngredientGroupDto

    abstract fun mapDetails(ingredient: Ingredient): RecipeResponse.RecipeDto.IngredientGroupDto.IngredientDto

}