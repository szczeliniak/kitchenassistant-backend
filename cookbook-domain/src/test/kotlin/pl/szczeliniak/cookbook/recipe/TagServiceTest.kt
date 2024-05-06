package pl.szczeliniak.cookbook.recipe

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.szczeliniak.cookbook.recipe.db.Tag
import pl.szczeliniak.cookbook.recipe.db.TagCriteria
import pl.szczeliniak.cookbook.recipe.db.TagDao
import pl.szczeliniak.cookbook.recipe.dto.response.TagsResponse
import pl.szczeliniak.cookbook.shared.ErrorCode
import pl.szczeliniak.cookbook.shared.CookBookException
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType
import pl.szczeliniak.cookbook.user.db.User
import java.util.*

class TagServiceTest {

    private val tagDao: TagDao = mockk()
    private val requestContext: RequestContext = mockk()
    private val tagService = TagService(tagDao, requestContext)

    @Test
    fun shouldReturnAll() {
        every { requestContext.tokenType() } returns TokenType.ACCESS
        every { requestContext.userId() } returns 1
        every { tagDao.findAll(TagCriteria("name"), 1) } returns Collections.singletonList(tag())

        val tags = tagService.getAll("name")

        assertEquals(TagsResponse(Collections.singletonList("name")), tags)
    }

    @Test
    fun shouldThrowExceptionWhenTokenTypeOtherThanAccess() {
        every { requestContext.tokenType() } returns TokenType.REFRESH

        val exception = assertThrows<CookBookException> { tagService.getAll("name") }

        assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    private fun tag(): Tag {
        return Tag(name = "name", user = User())
    }

}