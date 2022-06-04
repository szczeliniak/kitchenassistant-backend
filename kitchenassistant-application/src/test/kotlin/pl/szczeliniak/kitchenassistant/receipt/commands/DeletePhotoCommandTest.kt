package pl.szczeliniak.kitchenassistant.receipt.commands

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.receipt.FtpClient
import pl.szczeliniak.kitchenassistant.receipt.Photo
import pl.szczeliniak.kitchenassistant.receipt.PhotoDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.ZonedDateTime

internal class DeletePhotoCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @Mock
    private lateinit var photoDao: PhotoDao

    @InjectMocks
    private lateinit var deletePhotoCommand: DeletePhotoCommand

    @Test
    fun shouldDeletePhoto() {
        val file = photo()
        whenever(photoDao.findById(1)).thenReturn(file)
        whenever(photoDao.save(file)).thenReturn(file)

        val response = deletePhotoCommand.execute(1)

        Mockito.verify(ftpClient).delete("NAME")
        Assertions.assertEquals(true, file.deleted)
        Assertions.assertEquals(SuccessResponse(1), response)
    }

    private fun photo(): Photo {
        return Photo(1, "NAME", 4, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}