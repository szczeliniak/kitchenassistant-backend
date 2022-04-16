package pl.szczeliniak.kitchenassistant.files.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.files.FtpClient
import pl.szczeliniak.kitchenassistant.files.SupportedMediaType
import pl.szczeliniak.kitchenassistant.files.queries.dtos.GetPhotoFileResponse

internal class DownloadPhotoFileCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @InjectMocks
    private lateinit var downloadPhotoFileCommand: DownloadPhotoFileCommand

    @Test
    fun shouldReturnPhoto() {
        val bytes = ByteArray(10)
        whenever(ftpClient.download("NAME.jpg")).thenReturn(bytes)

        val result = downloadPhotoFileCommand.execute("NAME.jpg")

        assertThat(result).isEqualTo(GetPhotoFileResponse(SupportedMediaType.JPG, bytes))

    }

}