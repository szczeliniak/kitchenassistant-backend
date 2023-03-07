package pl.szczeliniak.kitchenassistant.recipe.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.Photo
import pl.szczeliniak.kitchenassistant.recipe.db.PhotoDao
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.GetPhotoResponse
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
        whenever(photoDao.findById(3)).thenReturn(photo())
        whenever(ftpClient.download("NAME.jpg")).thenReturn(bytes)

        val result = downloadPhotoQuery.execute(3)

        assertThat(result).isEqualTo(GetPhotoResponse(SupportedMediaType.JPG, bytes))
    }

    private fun photo(): Photo {
        return Photo(1, "NAME.jpg", 4, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}