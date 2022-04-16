package pl.szczeliniak.kitchenassistant.files.commands

import pl.szczeliniak.kitchenassistant.files.FtpClient

class DeletePhotoFileCommand(private val ftpClient: FtpClient) {

    fun execute(name: String) {
        ftpClient.delete(name)
    }

}