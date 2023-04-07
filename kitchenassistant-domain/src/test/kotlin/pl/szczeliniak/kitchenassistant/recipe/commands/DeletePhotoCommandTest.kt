package pl.szczeliniak.kitchenassistant.recipe.commands

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.db.Photo
import pl.szczeliniak.kitchenassistant.recipe.db.PhotoDao
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.ZonedDateTime
import java.util.*

internal class DeletePhotoCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @Mock
    private lateinit var recipeDao: RecipeDao

    @Mock
    private lateinit var photoDao: PhotoDao

    @InjectMocks
    private lateinit var deletePhotoCommand: DeletePhotoCommand

    @Test
    fun shouldDeletePhoto() {
        val photo = photo()
        val recipe = recipe()
        whenever(recipeDao.findById(1)).thenReturn(recipe)
        whenever(photoDao.findById(1)).thenReturn(photo)
        whenever(photoDao.save(photo)).thenReturn(photo)

        val response = deletePhotoCommand.execute(1)

        Mockito.verify(ftpClient).delete("NAME")
        Mockito.verify(recipeDao).save(recipe)

        Assertions.assertNull(recipe.photo)
        Assertions.assertEquals(true, photo.deleted)
        Assertions.assertEquals(SuccessResponse(1), response)
    }

    private fun recipe(): Recipe {
        return Recipe(1, "", 1, null, null, null, false, null, Collections.emptySet(), Collections.emptySet(), photo(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private fun photo(): Photo {
        return Photo(1, "NAME", 4, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}