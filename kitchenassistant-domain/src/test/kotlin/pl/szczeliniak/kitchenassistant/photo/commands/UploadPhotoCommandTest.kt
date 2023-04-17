package pl.szczeliniak.kitchenassistant.photo.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.photo.commands.UploadPhotoCommand
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.photo.commands.dto.UploadPhotoResponse

internal class UploadPhotoCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @InjectMocks
    private lateinit var uploadPhotoCommand: UploadPhotoCommand

    @Test
    fun shouldUploadPhoto() {
        val bytes = ByteArray(10)
        whenever(ftpClient.upload("PHOTO_NAME", bytes)).thenReturn("NAME")

        val response = uploadPhotoCommand.execute("PHOTO_NAME", bytes)

        assertThat(response).isEqualTo(UploadPhotoResponse("NAME"))
    }

}