package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.TagCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.TagDao
import pl.szczeliniak.kitchenassistant.recipe.dto.response.TagsResponse
import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType

open class TagService(
    private val tagDao: TagDao,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun getAll(name: String? = null): TagsResponse {
        requireTokenType(TokenType.ACCESS)
        return TagsResponse(tagDao.findAll(TagCriteria(name), requestContext.userId()).map { it.name })
    }

}