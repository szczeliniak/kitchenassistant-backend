package pl.szczeliniak.kitchenassistant.recipe

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import pl.szczeliniak.kitchenassistant.recipe.db.Tag
import pl.szczeliniak.kitchenassistant.recipe.db.TagCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.TagDao
import pl.szczeliniak.kitchenassistant.recipe.dto.response.TagsResponse
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType
import pl.szczeliniak.kitchenassistant.user.db.User
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

        val exception = assertThrows<KitchenAssistantException> { tagService.getAll("name") }

        assertEquals(exception.error, ErrorCode.WRONG_TOKEN_TYPE)
    }

    private fun tag(): Tag {
        return Tag(name = "name", user = User())
    }

}