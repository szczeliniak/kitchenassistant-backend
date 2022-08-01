package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.commands.*
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.*
import pl.szczeliniak.kitchenassistant.recipe.queries.*
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class RecipeFacadeImpl(
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
    private val assignRecipePhotosCommand: AssignRecipePhotosCommand,
    private val getTagsQuery: GetTagsQuery,
    private val markRecipeAsFavoriteCommand: MarkRecipeAsFavoriteCommand,
    private val getAuthorsQuery: GetAuthorsQuery,
    private val uploadPhotoCommand: UploadPhotoCommand,
    private val deletePhotoCommand: DeletePhotoCommand,
    private val downloadPhotoQuery: DownloadPhotoQuery,
    private val addIngredientGroupCommand: AddIngredientGroupCommand,
    private val deleteIngredientGroupCommand: DeleteIngredientGroupCommand
) : RecipeFacade {

    override fun getRecipe(recipeId: Int): RecipeResponse {
        return getRecipeQuery.execute(recipeId)
    }

    override fun getRecipes(page: Long?, limit: Int?, criteria: RecipeCriteria): RecipesResponse {
        return getRecipesQuery.execute(page, limit, criteria)
    }

    override fun addRecipe(dto: NewRecipeDto): SuccessResponse {
        return addRecipeCommand.execute(dto)
    }

    override fun updateRecipe(recipeId: Int, dto: UpdateRecipeDto): SuccessResponse {
        return updateRecipeCommand.execute(recipeId, dto)
    }

    override fun deleteRecipe(recipeId: Int): SuccessResponse {
        return deleteRecipeCommand.execute(recipeId)
    }

    override fun addStep(recipeId: Int, dto: NewStepDto): SuccessResponse {
        return addStepCommand.execute(recipeId, dto)
    }

    override fun updateStep(recipeId: Int, stepId: Int, dto: UpdateStepDto): SuccessResponse {
        return updateStepCommand.execute(recipeId, stepId, dto)
    }

    override fun deleteStep(recipeId: Int, stepId: Int): SuccessResponse {
        return deleteStepCommand.execute(recipeId, stepId)
    }

    override fun addIngredientGroup(recipeId: Int, dto: NewIngredientGroupDto): SuccessResponse {
        return addIngredientGroupCommand.execute(recipeId, dto)
    }

    override fun deleteIngredientGroup(recipeId: Int, ingredientGroupId: Int): SuccessResponse {
        return deleteIngredientGroupCommand.execute(recipeId, ingredientGroupId)
    }

    override fun addIngredient(recipeId: Int, ingredientGroupId: Int, dto: NewIngredientDto): SuccessResponse {
        return addIngredientCommand.execute(recipeId, ingredientGroupId, dto)
    }

    override fun updateIngredient(
        recipeId: Int,
        ingredientGroupId: Int,
        ingredientId: Int,
        dto: UpdateIngredientDto
    ): SuccessResponse {
        return updateIngredientCommand.execute(recipeId, ingredientGroupId, ingredientId, dto)
    }

    override fun deleteIngredient(recipeId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse {
        return deleteIngredientCommand.execute(recipeId, ingredientGroupId, ingredientId)
    }

    override fun addCategory(dto: NewCategoryDto): SuccessResponse {
        return addCategoryCommand.execute(dto)
    }

    override fun getCategories(criteria: CategoryCriteria): CategoriesResponse {
        return getCategoriesQuery.execute(criteria)
    }

    override fun deleteCategory(categoryId: Int): SuccessResponse {
        return deleteCategoryCommand.execute(categoryId)
    }

    override fun updateCategory(categoryId: Int, request: UpdateCategoryDto): SuccessResponse {
        return updateCategoryCommand.execute(categoryId, request)
    }

    override fun getTags(criteria: TagCriteria): TagsResponse {
        return getTagsQuery.execute(criteria)
    }

    override fun getAuthors(criteria: AuthorCriteria): AuthorsResponse {
        return getAuthorsQuery.execute(criteria)
    }

    override fun assignPhotosToRecipe(recipeId: Int, request: AssignPhotosToRecipeDto): SuccessResponse {
        return assignRecipePhotosCommand.execute(recipeId, request)
    }

    override fun markRecipeAsFavorite(recipeId: Int, isFavorite: Boolean): SuccessResponse {
        return markRecipeAsFavoriteCommand.execute(recipeId, isFavorite)
    }

    override fun uploadPhoto(userId: Int, name: String, bytes: ByteArray): SuccessResponse {
        return uploadPhotoCommand.execute(name, bytes, userId)
    }

    override fun downloadPhoto(photoId: Int): GetPhotoResponse {
        return downloadPhotoQuery.execute(photoId)
    }

    override fun deletePhoto(photoId: Int): SuccessResponse {
        return deletePhotoCommand.execute(photoId)
    }

}