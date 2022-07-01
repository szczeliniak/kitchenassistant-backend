package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.FtpClient
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeletePhotoCommand(private val ftpClient: FtpClient, private val photoDao: PhotoDao) {

    fun execute(id: Int): SuccessResponse {
        val photo = photoDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.PHOTO_NOT_FOUND)
        ftpClient.delete(photo.name)
        photo.deleted = true
        return SuccessResponse(photoDao.save(photo).id)
    }

}