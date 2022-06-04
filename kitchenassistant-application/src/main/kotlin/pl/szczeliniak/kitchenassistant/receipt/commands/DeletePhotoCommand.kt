package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.FtpClient
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DeletePhotoCommand(private val ftpClient: FtpClient, private val photoDao: PhotoDao) {

    fun execute(id: Int): SuccessResponse {
        val photo = photoDao.findById(id) ?: throw NotFoundException("Photo not found")
        ftpClient.delete(photo.name)
        photo.markAsDeleted()
        return SuccessResponse(photoDao.save(photo).id)
    }

}