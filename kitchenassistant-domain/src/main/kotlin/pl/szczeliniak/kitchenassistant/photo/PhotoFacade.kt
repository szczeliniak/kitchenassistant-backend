package pl.szczeliniak.kitchenassistant.photo

import pl.szczeliniak.kitchenassistant.photo.commands.DeletePhotoCommand
import pl.szczeliniak.kitchenassistant.photo.commands.UploadPhotoCommand
import pl.szczeliniak.kitchenassistant.photo.commands.dto.DeletePhotoResponse
import pl.szczeliniak.kitchenassistant.photo.commands.dto.UploadPhotoResponse
import pl.szczeliniak.kitchenassistant.photo.queries.DownloadPhotoQuery
import pl.szczeliniak.kitchenassistant.photo.queries.dto.GetPhotoResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

open class PhotoFacade(
        private val uploadPhotoCommand: UploadPhotoCommand,
        private val deletePhotoCommand: DeletePhotoCommand,
        private val downloadPhotoQuery: DownloadPhotoQuery
) {

    fun uploadPhoto(name: String, bytes: ByteArray): UploadPhotoResponse {
        return uploadPhotoCommand.execute(name, bytes)
    }

    fun downloadPhoto(fileName: String): GetPhotoResponse {
        return downloadPhotoQuery.execute(fileName)
    }

    fun deletePhoto(fileName: String): DeletePhotoResponse {
        return deletePhotoCommand.execute(fileName)
    }

}