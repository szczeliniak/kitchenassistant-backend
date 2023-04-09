package pl.szczeliniak.kitchenassistant.recipe.commands

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
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
        whenever(recipeDao.findById(1)).thenReturn(recipe)
        whenever(recipeDao.save(recipe)).thenReturn(recipe)

        val response = deletePhotoCommand.execute(1)

        Mockito.verify(recipeDao).save(recipe)
        Mockito.verify(ftpClient).delete("PHOTO_NAME")
        Assertions.assertNull(recipe.photoName)
        Assertions.assertEquals(SuccessResponse(1), response)
    }

    private fun recipe(): Recipe {
        return Recipe(1, "", 1, null, null, null, false, null, Collections.emptySet(), Collections.emptySet(), "PHOTO_NAME", Collections.emptySet(), ZonedDateTime.now(), ZonedDateTime.now())
    }

}