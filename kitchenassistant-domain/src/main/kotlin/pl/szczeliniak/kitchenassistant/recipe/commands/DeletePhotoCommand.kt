package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.PhotoDao
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeletePhotoCommand(private val ftpClient: FtpClient, private val recipeDao: RecipeDao, private val photoDao: PhotoDao) {

    fun execute(recipeId: Int): SuccessResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND)
        var photo = recipe.photo ?: throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND)
        ftpClient.delete(photo.name)
        recipe.photo = null
        recipeDao.save(recipe)

        photo = photoDao.findById(photo.id)!!
        photo.deleted = true
        return SuccessResponse(photoDao.save(photo).id)
    }

}