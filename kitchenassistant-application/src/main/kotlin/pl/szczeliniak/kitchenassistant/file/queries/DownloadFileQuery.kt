package pl.szczeliniak.kitchenassistant.file.queries

import pl.szczeliniak.kitchenassistant.exceptions.BadRequestException
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.file.FileDao
import pl.szczeliniak.kitchenassistant.file.FtpClient
import pl.szczeliniak.kitchenassistant.file.SupportedMediaType
import pl.szczeliniak.kitchenassistant.file.queries.dtos.GetFileResponse

class DownloadFileQuery(private val ftpClient: FtpClient, private val fileDao: FileDao) {

    fun execute(id: Int): GetFileResponse {
        val file = fileDao.findById(id) ?: throw NotFoundException("File not found")
        return GetFileResponse(
            SupportedMediaType.byExtension(file.name) ?: throw BadRequestException("Unsupported file media type"),
            ftpClient.download(file.name)
        )
    }

}