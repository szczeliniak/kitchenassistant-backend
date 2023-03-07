package pl.szczeliniak.kitchenassistant.recipe.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.Photo
import pl.szczeliniak.kitchenassistant.recipe.db.PhotoDao
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.PhotoFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.ZonedDateTime

internal class UploadPhotoCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @Mock
    private lateinit var photoDao: PhotoDao

    @Mock
    private lateinit var photoFactory: PhotoFactory

    @InjectMocks
    private lateinit var uploadPhotoCommand: UploadPhotoCommand

    @Test
    fun shouldUploadPhoto() {
        val photo = photo()
        val bytes = ByteArray(10)
        whenever(ftpClient.upload("PHOTO_NAME", bytes)).thenReturn("NAME")
        whenever(photoFactory.create("NAME", 4)).thenReturn(photo)
        whenever(photoDao.save(photo)).thenReturn(photo)

        val response = uploadPhotoCommand.execute("PHOTO_NAME", bytes, 4)

        assertThat(response).isEqualTo(SuccessResponse(1))
    }

    private fun photo(): Photo {
        return Photo(1, "NAME", 4, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}