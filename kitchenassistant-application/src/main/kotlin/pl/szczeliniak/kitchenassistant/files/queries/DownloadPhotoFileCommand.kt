package pl.szczeliniak.kitchenassistant.files.queries

import pl.szczeliniak.kitchenassistant.exceptions.BadRequestException
import pl.szczeliniak.kitchenassistant.files.FtpClient
import pl.szczeliniak.kitchenassistant.files.SupportedMediaType
import pl.szczeliniak.kitchenassistant.files.queries.dtos.GetPhotoFileResponse

class DownloadPhotoFileCommand(private val ftpClient: FtpClient) {

    fun execute(name: String): GetPhotoFileResponse {
        return GetPhotoFileResponse(
            SupportedMediaType.byExtension(name) ?: throw BadRequestException("Unsupported file media type"),
            ftpClient.download(name)
        )
    }

}