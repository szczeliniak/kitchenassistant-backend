package pl.szczeliniak.kitchenassistant.file.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.file.FtpClient
import pl.szczeliniak.kitchenassistant.receipt.FileDao

class DeleteFileCommand(private val ftpClient: FtpClient, private val fileDao: FileDao) {

    fun execute(id: Int): SuccessResponse {
        val file = fileDao.findById(id) ?: throw NotFoundException("File not found")
        ftpClient.delete(file.name)
        file.markAsDeleted()
        return SuccessResponse(fileDao.save(file).id)
    }

}