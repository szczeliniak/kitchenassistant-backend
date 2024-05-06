package pl.szczeliniak.cookbook.recipe

import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import pl.szczeliniak.cookbook.recipe.db.Author
import pl.szczeliniak.cookbook.recipe.db.Category
import pl.szczeliniak.cookbook.recipe.db.Ingredient
import pl.szczeliniak.cookbook.recipe.db.IngredientGroup
import pl.szczeliniak.cookbook.recipe.db.Recipe
import pl.szczeliniak.cookbook.recipe.db.Step
import pl.szczeliniak.cookbook.recipe.db.StepGroup
import pl.szczeliniak.cookbook.recipe.db.Tag
import pl.szczeliniak.cookbook.recipe.dto.request.NewCategoryRequest
import pl.szczeliniak.cookbook.recipe.dto.request.UpdateCategoryRequest
import pl.szczeliniak.cookbook.recipe.dto.response.CategoriesResponse
import pl.szczeliniak.cookbook.recipe.dto.response.RecipeResponse
import pl.szczeliniak.cookbook.recipe.dto.response.RecipesResponse
import pl.szczeliniak.cookbook.user.db.User

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
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    abstract fun category(id: Int, request: NewCategoryRequest, user: User): Category

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "modifiedAt", ignore = true)
    abstract fun category(@MappingTarget category: Category, updateCategoryRequest: UpdateCategoryRequest)

}