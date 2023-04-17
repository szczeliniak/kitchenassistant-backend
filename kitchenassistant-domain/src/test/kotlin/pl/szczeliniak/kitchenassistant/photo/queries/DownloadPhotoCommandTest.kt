package pl.szczeliniak.kitchenassistant.photo.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.photo.queries.DownloadPhotoQuery
import pl.szczeliniak.kitchenassistant.photo.queries.dto.GetPhotoResponse
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType

internal class DownloadPhotoCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @InjectMocks
    private lateinit var downloadPhotoQuery: DownloadPhotoQuery

    @Test
    fun shouldReturnPhoto() {
        val bytes = ByteArray(10)
        whenever(ftpClient.download("NAME.jpg")).thenReturn(bytes)

        val result = downloadPhotoQuery.execute("NAME.jpg")

        assertThat(result).isEqualTo(GetPhotoResponse(SupportedMediaType.JPG, bytes))
    }

}