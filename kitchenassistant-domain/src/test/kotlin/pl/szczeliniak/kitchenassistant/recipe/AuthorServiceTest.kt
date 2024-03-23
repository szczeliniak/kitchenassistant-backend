package pl.szczeliniak.kitchenassistant.recipe

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.szczeliniak.kitchenassistant.recipe.db.Author
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorDao
import pl.szczeliniak.kitchenassistant.recipe.dto.response.AuthorsResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.user.db.User
import java.util.*

class AuthorServiceTest {

    private val authorDao: AuthorDao = mockk()
    private val requestContext: RequestContext = mockk()
    private val authorService = AuthorService(authorDao, requestContext)

    @Test
    fun shouldReturnAll() {
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { authorDao.findAll(AuthorCriteria("name"), 1) } returns Collections.singletonList(author())

        val authors = authorService.getAll("name")

        assertEquals(AuthorsResponse(Collections.singletonList("name")), authors)
    }

    @Test
    fun shouldThrowExceptionWhenTokenTypeOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val exception = assertThrows<KitchenAssistantException> { authorService.getAll("name") }

        assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    private fun author(): Author {
        return Author(name = "name", user = User())
    }

}