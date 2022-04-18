package pl.szczeliniak.kitchenassistant.file.commands

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.file.FtpClient
import pl.szczeliniak.kitchenassistant.receipt.File
import pl.szczeliniak.kitchenassistant.receipt.FileDao
import java.time.LocalDateTime

internal class DeleteFileCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @Mock
    private lateinit var fileDao: FileDao

    @InjectMocks
    private lateinit var deleteFileCommand: DeleteFileCommand

    @Test
    fun shouldDeletePhoto() {
        val file = file()
        whenever(fileDao.findById(1)).thenReturn(file)
        whenever(fileDao.save(file)).thenReturn(file)

        val response = deleteFileCommand.execute(1)

        Mockito.verify(ftpClient).delete("NAME")
        Assertions.assertEquals(true, file.deleted)
        Assertions.assertEquals(SuccessResponse(1), response)
    }

    private fun file(): File {
        return File(1, "NAME", false, LocalDateTime.now(), LocalDateTime.now())
    }

}