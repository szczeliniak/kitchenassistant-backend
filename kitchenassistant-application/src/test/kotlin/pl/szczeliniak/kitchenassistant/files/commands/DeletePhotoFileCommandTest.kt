package pl.szczeliniak.kitchenassistant.files.commands

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.files.FtpClient

internal class DeletePhotoFileCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @InjectMocks
    private lateinit var deletePhotoFileCommand: DeletePhotoFileCommand

    @Test
    fun shouldDeletePhoto() {
        deletePhotoFileCommand.execute("NAME")

        Mockito.verify(ftpClient).delete("NAME")
    }

}