package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.PhotoDao
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.PhotoFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UploadPhotoCommand(
        private val ftpClient: FtpClient,
        private val photoDao: PhotoDao,
        private val photoFactory: PhotoFactory
) {

    fun execute(name: String, content: ByteArray, userId: Int): SuccessResponse {
        val photo = photoFactory.create(ftpClient.upload(name, content), userId)
        return SuccessResponse(photoDao.save(photo).id)
    }

}