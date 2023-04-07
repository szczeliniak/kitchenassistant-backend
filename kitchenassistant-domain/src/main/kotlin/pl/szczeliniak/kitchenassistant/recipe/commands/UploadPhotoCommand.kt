package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UploadPhotoResponse

class UploadPhotoCommand(
        private val ftpClient: FtpClient
) {

    fun execute(name: String, content: ByteArray): UploadPhotoResponse {
        return UploadPhotoResponse(ftpClient.upload(name, content))
    }

}