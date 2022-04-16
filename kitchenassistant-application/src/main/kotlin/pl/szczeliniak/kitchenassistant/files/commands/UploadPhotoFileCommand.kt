package pl.szczeliniak.kitchenassistant.files.commands

import pl.szczeliniak.kitchenassistant.files.FtpClient
import pl.szczeliniak.kitchenassistant.files.commands.dto.AddPhotoFileResponse

class UploadPhotoFileCommand(private val ftpClient: FtpClient) {

    fun execute(name: String, content: ByteArray): AddPhotoFileResponse {
        return AddPhotoFileResponse(ftpClient.upload(name, content))
    }

}