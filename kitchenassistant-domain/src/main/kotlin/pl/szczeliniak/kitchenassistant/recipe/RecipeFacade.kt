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
        private val addIngredientCommand: AddIngredientCommand,
        private val deleteIngredientCommand: DeleteIngredientCommand,
        private val updateIngredientCommand: UpdateIngredientCommand,
        private val addStepCommand: AddStepCommand,
        private val deleteStepCommand: DeleteStepCommand,
        private val deleteCategoryCommand: DeleteCategoryCommand,
        private val updateCategoryCommand: UpdateCategoryCommand,
        private val updateStepCommand: UpdateStepCommand,
        private val getCategoriesQuery: GetCategoriesQuery,
        private val getTagsQuery: GetTagsQuery,
        private val markRecipeAsFavoriteCommand: MarkRecipeAsFavoriteCommand,
        private val getAuthorsQuery: GetAuthorsQuery,
        private val uploadPhotoCommand: UploadPhotoCommand,
        private val deletePhotoCommand: DeletePhotoCommand,
        private val cleanupOrphanedPhotosCommand: CleanupOrphanedPhotosCommand,
        private val downloadPhotoQuery: DownloadPhotoQuery,
        private val addIngredientGroupCommand: AddIngredientGroupCommand,
        private val deleteIngredientGroupCommand: DeleteIngredientGroupCommand
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

    fun deleteIngredientGroup(recipeId: Int, ingredientGroupId: Int): SuccessResponse {
        return deleteIngredientGroupCommand.execute(recipeId, ingredientGroupId)
    }

    fun addIngredient(recipeId: Int, ingredientGroupId: Int, dto: NewIngredientDto): SuccessResponse {
        return addIngredientCommand.execute(recipeId, ingredientGroupId, dto)
    }

    fun updateIngredient(
            recipeId: Int,
            ingredientGroupId: Int,
            ingredientId: Int,
            dto: UpdateIngredientDto
    ): SuccessResponse {
        return updateIngredientCommand.execute(recipeId, ingredientGroupId, ingredientId, dto)
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

    fun uploadPhoto(userId: Int, name: String, bytes: ByteArray): SuccessResponse {
        return uploadPhotoCommand.execute(name, bytes, userId)
    }

    fun downloadPhoto(recipeId: Int): GetPhotoResponse {
        return downloadPhotoQuery.execute(recipeId)
    }

    fun deletePhoto(recipeId: Int): SuccessResponse {
        return deletePhotoCommand.execute(recipeId)
    }

    fun cleanupOrphanedPhotos() {
        cleanupOrphanedPhotosCommand.execute()
    }

}