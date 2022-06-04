package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.FtpClient
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UploadPhotoCommand(
    private val ftpClient: FtpClient,
    private val photoDao: PhotoDao,
    private val photoFactory: PhotoFactory
) {

    fun execute(name: String, content: ByteArray, userId: Int): SuccessResponse {
        val file = photoFactory.create(ftpClient.upload(name, content), userId)
        return SuccessResponse(photoDao.save(file).id)
    }

}