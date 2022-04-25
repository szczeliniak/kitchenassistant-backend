package pl.szczeliniak.kitchenassistant.file.queries

import pl.szczeliniak.kitchenassistant.file.FileDao
import pl.szczeliniak.kitchenassistant.file.FtpClient
import pl.szczeliniak.kitchenassistant.file.SupportedMediaType
import pl.szczeliniak.kitchenassistant.file.queries.dtos.GetFileResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.BadRequestException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DownloadFileQuery(private val ftpClient: FtpClient, private val fileDao: FileDao) {

    fun execute(id: Int): GetFileResponse {
        val file = fileDao.findById(id) ?: throw NotFoundException("File not found")
        return GetFileResponse(
            SupportedMediaType.byFileName(file.name) ?: throw BadRequestException("Unsupported file media type"),
            ftpClient.download(file.name)
        )
    }

}