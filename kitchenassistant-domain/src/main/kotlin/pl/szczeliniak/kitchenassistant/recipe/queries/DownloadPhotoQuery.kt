package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.GetPhotoResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

class DownloadPhotoQuery(private val ftpClient: FtpClient, private val recipeDao: RecipeDao) {

    fun execute(recipeId: Int): GetPhotoResponse {
        val recipe = recipeDao.findById(recipeId) ?: throw KitchenAssistantException(ErrorCode.RECIPE_NOT_FOUND)
        val photo = recipe.photoName ?: throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND)
        return GetPhotoResponse(
                SupportedMediaType.byFileName(photo)
                        ?: throw KitchenAssistantException(ErrorCode.UNSUPPORTED_MEDIA_TYPE),
                ftpClient.download(photo)
        )
    }

}