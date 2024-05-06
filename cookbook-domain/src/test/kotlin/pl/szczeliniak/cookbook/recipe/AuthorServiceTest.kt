package pl.szczeliniak.cookbook.recipe

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.szczeliniak.cookbook.recipe.db.Author
import pl.szczeliniak.cookbook.recipe.db.AuthorCriteria
import pl.szczeliniak.cookbook.recipe.db.AuthorDao
import pl.szczeliniak.cookbook.recipe.dto.response.AuthorsResponse
import pl.szczeliniak.cookbook.shared.ErrorCode
import pl.szczeliniak.cookbook.shared.CookBookException
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType
import pl.szczeliniak.cookbook.user.db.User
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

        val exception = assertThrows<CookBookException> { authorService.getAll("name") }

        assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    private fun author(): Author {
        return Author(name = "name", user = User())
    }

}