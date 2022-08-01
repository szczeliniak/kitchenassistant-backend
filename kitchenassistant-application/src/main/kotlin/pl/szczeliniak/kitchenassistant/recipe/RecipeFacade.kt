package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.*
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.*
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

interface RecipeFacade {

    fun getRecipe(recipeId: Int): RecipeResponse
    fun getRecipes(page: Long?, limit: Int?, criteria: RecipeCriteria): RecipesResponse
    fun addRecipe(dto: NewRecipeDto): SuccessResponse
    fun updateRecipe(recipeId: Int, dto: UpdateRecipeDto): SuccessResponse
    fun deleteRecipe(recipeId: Int): SuccessResponse
    fun addStep(recipeId: Int, dto: NewStepDto): SuccessResponse
    fun updateStep(recipeId: Int, stepId: Int, dto: UpdateStepDto): SuccessResponse
    fun deleteStep(recipeId: Int, stepId: Int): SuccessResponse
    fun addIngredientGroup(recipeId: Int, dto: NewIngredientGroupDto): SuccessResponse
    fun deleteIngredientGroup(recipeId: Int, ingredientGroupId: Int): SuccessResponse
    fun addIngredient(recipeId: Int, ingredientGroupId: Int, dto: NewIngredientDto): SuccessResponse
    fun updateIngredient(
        recipeId: Int,
        ingredientGroupId: Int,
        ingredientId: Int,
        dto: UpdateIngredientDto
    ): SuccessResponse

    fun deleteIngredient(recipeId: Int, ingredientGroupId: Int, ingredientId: Int): SuccessResponse
    fun addCategory(dto: NewCategoryDto): SuccessResponse
    fun getCategories(criteria: CategoryCriteria): CategoriesResponse
    fun deleteCategory(categoryId: Int): SuccessResponse
    fun updateCategory(categoryId: Int, request: UpdateCategoryDto): SuccessResponse
    fun getTags(criteria: TagCriteria): TagsResponse
    fun getAuthors(criteria: AuthorCriteria): AuthorsResponse
    fun assignPhotosToRecipe(recipeId: Int, request: AssignPhotosToRecipeDto): SuccessResponse
    fun markRecipeAsFavorite(recipeId: Int, isFavorite: Boolean): SuccessResponse
    fun uploadPhoto(userId: Int, name: String, bytes: ByteArray): SuccessResponse
    fun downloadPhoto(photoId: Int): GetPhotoResponse
    fun deletePhoto(photoId: Int): SuccessResponse

}