package pl.szczeliniak.kitchenassistant.file.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.file.File
import pl.szczeliniak.kitchenassistant.file.FileDao
import pl.szczeliniak.kitchenassistant.file.FtpClient
import pl.szczeliniak.kitchenassistant.file.commands.factories.FileFactory
import java.time.LocalDateTime

internal class UploadFileCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @Mock
    private lateinit var fileDao: FileDao

    @Mock
    private lateinit var fileFactory: FileFactory

    @InjectMocks
    private lateinit var uploadFileCommand: UploadFileCommand

    @Test
    fun shouldUploadPhoto() {
        val file = file()
        val bytes = ByteArray(10)
        whenever(ftpClient.upload("FILE_NAME", bytes)).thenReturn("NAME")
        whenever(fileFactory.create("NAME", 4)).thenReturn(file)
        whenever(fileDao.save(file)).thenReturn(file)

        val response = uploadFileCommand.execute("FILE_NAME", bytes, 4)

        assertThat(response).isEqualTo(SuccessResponse(1))
    }

    private fun file(): File {
        return File(1, "NAME", 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

}