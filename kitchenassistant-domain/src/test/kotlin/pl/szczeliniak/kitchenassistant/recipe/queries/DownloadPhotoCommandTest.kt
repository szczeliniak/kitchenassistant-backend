package pl.szczeliniak.kitchenassistant.recipe.queries

import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import pl.szczeliniak.kitchenassistant.JunitBaseClass
import pl.szczeliniak.kitchenassistant.recipe.FtpClient
import pl.szczeliniak.kitchenassistant.recipe.SupportedMediaType
import pl.szczeliniak.kitchenassistant.recipe.db.Photo
import pl.szczeliniak.kitchenassistant.recipe.db.Recipe
import pl.szczeliniak.kitchenassistant.recipe.db.RecipeDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.GetPhotoResponse
import java.time.ZonedDateTime
import java.util.*

internal class DownloadPhotoCommandTest : JunitBaseClass() {

    @Mock
    private lateinit var ftpClient: FtpClient

    @Mock
    private lateinit var recipeDao: RecipeDao

    @InjectMocks
    private lateinit var downloadPhotoQuery: DownloadPhotoQuery

    @Test
    fun shouldReturnPhoto() {
        val bytes = ByteArray(10)
        whenever(recipeDao.findById(3)).thenReturn(recipe())
        whenever(ftpClient.download("NAME.jpg")).thenReturn(bytes)

        val result = downloadPhotoQuery.execute(3)

        assertThat(result).isEqualTo(GetPhotoResponse(SupportedMediaType.JPG, bytes))
    }

    private fun recipe(): Recipe {
        return Recipe(1, "", 1, null, null, null, false, null, Collections.emptySet(), Collections.emptySet(), photo(), Collections.emptySet(), false, ZonedDateTime.now(), ZonedDateTime.now())
    }

    private fun photo(): Photo {
        return Photo(1, "NAME.jpg", 4, false, ZonedDateTime.now(), ZonedDateTime.now())
    }

}