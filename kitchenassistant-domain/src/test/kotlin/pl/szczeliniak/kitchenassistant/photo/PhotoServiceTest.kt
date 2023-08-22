package pl.szczeliniak.kitchenassistant.photo

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.photo.dto.response.DeletePhotoResponse
import pl.szczeliniak.kitchenassistant.photo.dto.response.GetPhotoResponse
import pl.szczeliniak.kitchenassistant.photo.dto.response.UploadPhotoResponse
import pl.szczeliniak.kitchenassistant.shared.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.user.db.User
import java.time.ZonedDateTime
import java.util.*

internal class PhotoServiceTest : JunitBaseClass() {

    @Mock
    private lateinit var recipeDao: RecipeDao

    @Mock
    private lateinit var ftpClient: FtpClient

    @InjectMocks
    private lateinit var photoService: PhotoService

    @Test
    fun shouldDeletePhoto() {
        val recipe = recipe()
        whenever(recipeDao.findAll(RecipeCriteria(false, null, null, null, null, "PHOTO_NAME"), 0, 1000)).thenReturn(
            setOf(recipe)
        )

        val response = photoService.delete("PHOTO_NAME")

        Mockito.verify(recipeDao).save(setOf(recipe))
        Mockito.verify(ftpClient).delete("PHOTO_NAME")
        Assertions.assertNull(recipe.photoName)
        Assertions.assertEquals(DeletePhotoResponse("PHOTO_NAME"), response)
    }

    @Test
    fun shouldUploadPhoto() {
        val bytes = ByteArray(10)
        whenever(ftpClient.upload("PHOTO_NAME", bytes)).thenReturn("NAME")

        val response = photoService.upload("PHOTO_NAME", bytes)

        assertThat(response).isEqualTo(UploadPhotoResponse("NAME"))
    }

    @Test
    fun shouldReturnPhoto() {
        val bytes = ByteArray(10)
        whenever(ftpClient.download("NAME.jpg")).thenReturn(bytes)

        val result = photoService.download("NAME.jpg")

        assertThat(result).isEqualTo(GetPhotoResponse(SupportedMediaType.JPG, bytes))
    }

    private fun recipe(): Recipe {
        return Recipe(
            1,
            "",
            User(id = 1, email = ""),
            null,
            null,
            null,
            false,
            null,
            Collections.emptySet(),
            Collections.emptySet(),
            "PHOTO_NAME",
            Collections.emptySet(),
            ZonedDateTime.now(),
            ZonedDateTime.now()
        )
    }

}