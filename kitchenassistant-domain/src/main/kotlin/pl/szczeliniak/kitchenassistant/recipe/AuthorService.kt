package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.AuthorCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorDao
import pl.szczeliniak.kitchenassistant.recipe.dto.response.AuthorsResponse
import pl.szczeliniak.kitchenassistant.shared.BaseService
import pl.szczeliniak.kitchenassistant.shared.RequestContext
import pl.szczeliniak.kitchenassistant.shared.TokenType

open class AuthorService(
    private val authorDao: AuthorDao,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun getAll(name: String? = null): AuthorsResponse {
        requireTokenType(TokenType.ACCESS)
        return AuthorsResponse(authorDao.findAll(AuthorCriteria(name), requestContext.userId()).map { it.name })
    }

}