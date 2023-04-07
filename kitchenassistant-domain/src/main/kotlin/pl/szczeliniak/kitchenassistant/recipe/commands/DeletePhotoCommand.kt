package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeletePhotoCommand(private val recipeDao: RecipeDao, private val ftpClient: FtpClient) {

    fun execute(recipeId: Int): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        if (recipe.photoName == null) {
            return SuccessResponse(recipe.id)
        }

        recipe.photoName?.let {
            ftpClient.delete(it)
            recipe.photoName = null
        }
        return SuccessResponse(recipeDao.save(recipe).id)
    }

}