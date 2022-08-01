package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.PhotoDao
import pl.szczeliniak.kitchenassistant.recipe.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.AssignPhotosToRecipeDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AssignRecipePhotosCommand(
    private val recipeDao: RecipeDao,
    private val photoDao: PhotoDao
) {

    fun execute(id: Int, request: AssignPhotosToRecipeDto): SuccessResponse {
        val recipe = recipeDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)

        request.ids.forEach {
            if (recipe.photos.any { photo -> photo.id == it }) {
                return@forEach
            }
            recipe.photos.add(photoDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND))
        }

        return SuccessResponse(recipeDao.save(recipe).id)
    }

}