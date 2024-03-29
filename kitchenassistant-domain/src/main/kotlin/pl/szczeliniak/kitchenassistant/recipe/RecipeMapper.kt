package pl.szczeliniak.kitchenassistant.recipe

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.Category
import pl.szczeliniak.kitchenassistant.recipe.db.Ingredient
import pl.szczeliniak.kitchenassistant.recipe.db.IngredientGroup
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.recipe.db.StepGroup
import pl.szczeliniak.kitchenassistant.recipe.db.Tag
import pl.szczeliniak.kitchenassistant.recipe.dto.request.NewCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.request.UpdateCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.dto.response.CategoriesResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipeResponse
import pl.szczeliniak.kitchenassistant.recipe.dto.response.RecipesResponse
import pl.szczeliniak.kitchenassistant.user.db.User

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

    abstract fun mapDetails(stepGroup: StepGroup): RecipeResponse.Recipe.StepGroup

    abstract fun mapDetails(step: Step): RecipeResponse.Recipe.StepGroup.Step

    abstract fun mapDetails(ingredientGroup: IngredientGroup): RecipeResponse.Recipe.IngredientGroup

    abstract fun mapDetails(ingredient: Ingredient): RecipeResponse.Recipe.IngredientGroup.Ingredient

    abstract fun mapCategory(category: Category): CategoriesResponse.Category

    @Mapping(target = "name", source = "request.name")
    abstract fun category(id: Int, request: NewCategoryRequest, user: User): Category

    abstract fun category(@MappingTarget category: Category, updateCategoryRequest: UpdateCategoryRequest)

}