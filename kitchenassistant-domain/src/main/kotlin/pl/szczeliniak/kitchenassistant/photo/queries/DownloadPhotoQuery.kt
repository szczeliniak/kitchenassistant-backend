package pl.szczeliniak.kitchenassistant.photo.queries

import pl.szczeliniak.kitchenassistant.photo.queries.dto.GetPhotoResponse
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException

class DownloadPhotoQuery(private val ftpClient: FtpClient) {

    fun execute(fileName: String): GetPhotoResponse {
        return GetPhotoResponse(
                SupportedMediaType.byFileName(fileName)
                        ?: throw KitchenAssistantException(ErrorCode.UNSUPPORTED_MEDIA_TYPE),
                ftpClient.download(fileName)
        )
    }

}