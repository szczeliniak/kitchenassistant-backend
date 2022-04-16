package pl.szczeliniak.kitchenassistant.files.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.files.FtpClient
import pl.szczeliniak.kitchenassistant.files.commands.dto.AddPhotoFileResponse

internal class UploadPhotoFileCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @InjectMocks
    private lateinit var uploadPhotoFileCommand: UploadPhotoFileCommand

    @Test
    fun shouldUploadPhoto() {
        val bytes = ByteArray(10)
        whenever(ftpClient.upload("FILE_NAME", bytes)).thenReturn("NAME")

        val response = uploadPhotoFileCommand.execute("FILE_NAME", bytes)

        assertThat(response).isEqualTo(AddPhotoFileResponse("NAME"))
    }

}