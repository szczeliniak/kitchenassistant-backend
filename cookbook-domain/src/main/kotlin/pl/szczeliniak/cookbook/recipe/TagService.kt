package pl.szczeliniak.cookbook.recipe

import pl.szczeliniak.cookbook.recipe.db.TagCriteria
import pl.szczeliniak.cookbook.recipe.db.TagDao
import pl.szczeliniak.cookbook.recipe.dto.response.TagsResponse
import pl.szczeliniak.cookbook.shared.BaseService
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType

open class TagService(
    private val tagDao: TagDao,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun getAll(name: String? = null): TagsResponse {
        requireTokenType(TokenType.ACCESS)
        return TagsResponse(tagDao.findAll(TagCriteria(name), requestContext.userId()).map { it.name })
    }

}