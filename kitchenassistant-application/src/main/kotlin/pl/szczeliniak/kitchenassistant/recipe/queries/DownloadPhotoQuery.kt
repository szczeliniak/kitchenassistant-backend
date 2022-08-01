package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.PhotoDao
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.GetPhotoResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

class DownloadPhotoQuery(private val ftpClient: FtpClient, private val photoDao: PhotoDao) {

    fun execute(id: Int): GetPhotoResponse {
        val photo = photoDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND)
        return GetPhotoResponse(
            SupportedMediaType.byFileName(photo.name)
                ?: throw KitchenAssistantException(ErrorCode.UNSUPPORTED_MEDIA_TYPE),
            ftpClient.download(photo.name)
        )
    }

}