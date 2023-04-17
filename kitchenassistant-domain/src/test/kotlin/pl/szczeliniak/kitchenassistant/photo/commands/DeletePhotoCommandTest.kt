package pl.szczeliniak.kitchenassistant.photo.commands

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.photo.commands.DeletePhotoCommand
import pl.szczeliniak.kitchenassistant.photo.commands.dto.DeletePhotoResponse
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.ZonedDateTime
import java.util.*

internal class DeletePhotoCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var recipeDao: RecipeDao

    @Mock
    private lateinit var ftpClient: FtpClient

    @InjectMocks
    private lateinit var deletePhotoCommand: DeletePhotoCommand

    @Test
    fun shouldDeletePhoto() {
        val recipe = recipe()
        whenever(recipeDao.findAll(RecipeCriteria(false, null, null, null, null, "PHOTO_NAME"), 0, 1000)).thenReturn(setOf(recipe))

        val response = deletePhotoCommand.execute("PHOTO_NAME")

        Mockito.verify(recipeDao).save(setOf(recipe))
        Mockito.verify(ftpClient).delete("PHOTO_NAME")
        Assertions.assertNull(recipe.photoName)
        Assertions.assertEquals(DeletePhotoResponse("PHOTO_NAME"), response)
    }

    private fun recipe(): Recipe {
        return Recipe(1, "", 1, null, null, null, false, null, Collections.emptySet(), Collections.emptySet(), "PHOTO_NAME", Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

}