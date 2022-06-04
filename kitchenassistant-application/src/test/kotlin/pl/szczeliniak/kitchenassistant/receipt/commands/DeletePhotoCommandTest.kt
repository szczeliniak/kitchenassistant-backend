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
        val photo = photo()
        whenever(photoDao.findById(1)).thenReturn(photo)
        whenever(photoDao.save(photo)).thenReturn(photo)

        val response = deletePhotoCommand.execute(1)

        Mockito.verify(ftpClient).delete("NAME")
        Assertions.assertEquals(true, photo.deleted)
        Assertions.assertEquals(SuccessResponse(1), response)
    }

    private fun photo(): Photo {
        return Photo(1, "NAME", 4, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}