package pl.szczeliniak.kitchenassistant.photo

import pl.szczeliniak.kitchenassistant.photo.dto.response.DeletePhotoResponse
import pl.szczeliniak.kitchenassistant.photo.dto.response.GetPhotoResponse
import pl.szczeliniak.kitchenassistant.photo.dto.response.UploadPhotoResponse
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.FtpClient
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

open class PhotoService(
    private val ftpClient: FtpClient,
    private val recipeDao: RecipeDao
) {

    fun upload(name: String, bytes: ByteArray): UploadPhotoResponse {
        return UploadPhotoResponse(ftpClient.upload(name, bytes))
    }

    fun download(fileName: String): GetPhotoResponse {
        return GetPhotoResponse(
            SupportedMediaType.byFileName(fileName)
                ?: throw KitchenAssistantException(ErrorCode.UNSUPPORTED_MEDIA_TYPE),
            ftpClient.download(fileName)
        )
    }

    fun delete(fileName: String): DeletePhotoResponse {
        val recipes = recipeDao.findAll(RecipeCriteria(false, null, null, null, null, fileName), 0, 1000)
        recipes.forEach {
            it.photoName = null
        }
        recipeDao.save(recipes)
        ftpClient.delete(fileName)

        return DeletePhotoResponse(fileName)
    }

}