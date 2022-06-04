package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.FtpClient
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.SupportedMediaType
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.GetPhotoResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.BadRequestException
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DownloadPhotoQuery(private val ftpClient: FtpClient, private val photoDao: PhotoDao) {

    fun execute(id: Int): GetPhotoResponse {
        val photo = photoDao.findById(id) ?: throw NotFoundException("Photo not found")
        return GetPhotoResponse(
            SupportedMediaType.byFileName(photo.name) ?: throw BadRequestException("Unsupported file media type"),
            ftpClient.download(photo.name)
        )
    }

}