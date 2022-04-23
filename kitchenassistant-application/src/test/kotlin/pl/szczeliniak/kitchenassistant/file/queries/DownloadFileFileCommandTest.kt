package pl.szczeliniak.kitchenassistant.file.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.file.FtpClient
import pl.szczeliniak.kitchenassistant.file.SupportedMediaType
import pl.szczeliniak.kitchenassistant.file.queries.dtos.GetFileResponse
import pl.szczeliniak.kitchenassistant.receipt.File
import pl.szczeliniak.kitchenassistant.receipt.FileDao
import java.time.LocalDateTime

internal class DownloadFileFileCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @Mock
    private lateinit var fileDao: FileDao

    @InjectMocks
    private lateinit var downloadFileCommand: DownloadFileCommand

    @Test
    fun shouldReturnPhoto() {
        val bytes = ByteArray(10)
        whenever(fileDao.findById(3)).thenReturn(file())
        whenever(ftpClient.download("NAME.jpg")).thenReturn(bytes)

        val result = downloadFileCommand.execute(3)

        assertThat(result).isEqualTo(GetFileResponse(SupportedMediaType.JPG, bytes))
    }

    private fun file(): File {
        return File(1, "NAME.jpg", 4, false, LocalDateTime.now(), LocalDateTime.now())
    }

}