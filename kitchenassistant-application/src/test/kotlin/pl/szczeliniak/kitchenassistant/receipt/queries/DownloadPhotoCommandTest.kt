package pl.szczeliniak.kitchenassistant.receipt.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.receipt.FtpClient
import pl.szczeliniak.kitchenassistant.receipt.Photo
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.receipt.SupportedMediaType
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.GetPhotoResponse
import java.time.ZonedDateTime

internal class DownloadPhotoCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @Mock
    private lateinit var photoDao: PhotoDao

    @InjectMocks
    private lateinit var downloadPhotoQuery: DownloadPhotoQuery

    @Test
    fun shouldReturnPhoto() {
        val bytes = ByteArray(10)
        whenever(photoDao.findById(3)).thenReturn(file())
        whenever(ftpClient.download("NAME.jpg")).thenReturn(bytes)

        val result = downloadPhotoQuery.execute(3)

        assertThat(result).isEqualTo(GetPhotoResponse(SupportedMediaType.JPG, bytes))
    }

    private fun file(): Photo {
        return Photo(1, "NAME.jpg", 4, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}