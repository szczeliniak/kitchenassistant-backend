package pl.szczeliniak.kitchenassistant.photo

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import pl.szczeliniak.kitchenassistant.photo.dto.response.DeletePhotoResponse
import pl.szczeliniak.kitchenassistant.photo.dto.response.GetPhotoResponse
import pl.szczeliniak.kitchenassistant.photo.dto.response.UploadPhotoResponse
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.FtpClient
import pl.szczeliniak.kitchenassistant.user.db.User
import java.time.ZonedDateTime
import java.util.*

internal class PhotoServiceTest {

    private val recipeDao: RecipeDao = mockk();
    private val ftpClient: FtpClient = mockk();
    private val photoService: PhotoService = PhotoService(ftpClient, recipeDao)

    @Test
    fun shouldDeletePhoto() {
        val recipe = recipe()
        every { recipeDao.findAll(RecipeCriteria(false, null, null, null, null, "PHOTO_NAME"), 0, 1000) } returns setOf(recipe)
        justRun { recipeDao.save(setOf(recipe)) }
        justRun { ftpClient.delete("PHOTO_NAME") }
        val response = photoService.delete("PHOTO_NAME")

        verify(exactly = 1) { recipeDao.save(setOf(recipe)) }
        verify(exactly = 1) { ftpClient.delete("PHOTO_NAME") }

        assertThat(recipe.photoName).isNull();
        assertThat(response).isEqualTo(DeletePhotoResponse("PHOTO_NAME"))
    }


    @Test
    fun shouldUploadPhoto() {
        val bytes = ByteArray(10)
        every { ftpClient.upload("PHOTO_NAME", bytes) } returns "NAME"

        val response = photoService.upload("PHOTO_NAME", bytes)

        assertThat(response).isEqualTo(UploadPhotoResponse("NAME"))
    }

    @Test
    fun shouldReturnPhoto() {
        val bytes = ByteArray(10)
        every { ftpClient.download("NAME.jpg") } returns bytes;

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