package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.commands.*
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.*
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.CategoryCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.TagCriteria
import pl.szczeliniak.kitchenassistant.recipe.queries.*
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class RecipeFacade(
        private val getRecipeQuery: GetRecipeQuery,
        private val getRecipesQuery: GetRecipesQuery,
        private val addRecipeCommand: AddRecipeCommand,
        private val addCategoryCommand: AddCategoryCommand,
        private val deleteRecipeCommand: DeleteRecipeCommand,
        private val updateRecipeCommand: UpdateRecipeCommand,
        private val addStepCommand: AddStepCommand,
        private val deleteStepCommand: DeleteStepCommand,
        private val deleteCategoryCommand: DeleteCategoryCommand,
        private val updateCategoryCommand: UpdateCategoryCommand,
        private val updateStepCommand: UpdateStepCommand,
        private val getCategoriesQuery: GetCategoriesQuery,
        private val getTagsQuery: GetTagsQuery,
        private val markRecipeAsFavoriteCommand: MarkRecipeAsFavoriteCommand,
        private val getAuthorsQuery: GetAuthorsQuery,
        private val addIngredientGroupCommand: AddIngredientGroupCommand,
        private val updateIngredientGroupCommand: UpdateIngredientGroupCommand,
        private val deleteIngredientGroupCommand: DeleteIngredientGroupCommand,
        private val deleteIngredientCommand: DeleteIngredientCommand,
        private val getIngredientGroupQuery: GetIngredientGroupQuery
) {

    open fun findById(recipeId: Int): RecipeResponse {
        return getRecipeQuery.execute(recipeId)
    }

    fun findAll(page: Long?, limit: Int?, criteria: RecipeCriteria): RecipesResponse {
        return getRecipesQuery.execute(page, limit, criteria)
    }

    fun add(dto: NewRecipeDto): SuccessResponse {
        return addRecipeCommand.execute(dto)
    }

    fun update(recipeId: Int, dto: UpdateRecipeDto): SuccessResponse {
        return updateRecipeCommand.execute(recipeId, dto)
    }

    fun delete(recipeId: Int): SuccessResponse {
        return deleteRecipeCommand.execute(recipeId)
    }

    fun addStep(recipeId: Int, dto: NewStepDto): SuccessResponse {
        return addStepCommand.execute(recipeId, dto)
    }

    fun updateStep(recipeId: Int, stepId: Int, dto: UpdateStepDto): SuccessResponse {
        return updateStepCommand.execute(recipeId, stepId, dto)
    }

    fun deleteStep(recipeId: Int, stepId: Int): SuccessResponse {
        return deleteStepCommand.execute(recipeId, stepId)
    }

    fun addIngredientGroup(recipeId: Int, dto: NewIngredientGroupDto): SuccessResponse {
        return addIngredientGroupCommand.execute(recipeId, dto)
    }

    fun updateIngredientGroup(
            recipeId: Int,
            ingredientGroupId: Int,
            request: UpdateIngredientGroupDto
    ): SuccessResponse {
        return updateIngredientGroupCommand.execute(recipeId, ingredientGroupId, request)

    }

    fun deleteIngredientGroup(recipeId: Int, ingredientGroupId: Int): SuccessResponse {
        return deleteIngredientGroupCommand.execute(recipeId, ingredientGroupId)
    }

    fun deleteIngredient(recipeId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        return deleteIngredientCommand.execute(recipeId, ingredientGroupId, ingredientId)
    }

    fun addCategory(dto: NewCategoryDto): SuccessResponse {
        return addCategoryCommand.execute(dto)
    }

    fun getCategories(criteria: CategoryCriteria): CategoriesResponse {
        return getCategoriesQuery.execute(criteria)
    }

    fun deleteCategory(categoryId: Int): SuccessResponse {
        return deleteCategoryCommand.execute(categoryId)
    }

    fun updateCategory(categoryId: Int, request: UpdateCategoryDto): SuccessResponse {
        return updateCategoryCommand.execute(categoryId, request)
    }

    fun getTags(criteria: TagCriteria): TagsResponse {
        return getTagsQuery.execute(criteria)
    }

    fun getAuthors(criteria: AuthorCriteria): AuthorsResponse {
        return getAuthorsQuery.execute(criteria)
    }

    fun markAsFavorite(recipeId: Int, isFavorite: Boolean): SuccessResponse {
        return markRecipeAsFavoriteCommand.execute(recipeId, isFavorite)
    }

    fun getIngredientGroup(recipeId: Int, ingredientGroupId: Int): IngredientGroupResponse {
        return getIngredientGroupQuery.execute(recipeId, ingredientGroupId)
    }

}