package pl.szczeliniak.cookbook.recipe

import pl.szczeliniak.cookbook.recipe.db.AuthorCriteria
import pl.szczeliniak.cookbook.recipe.db.AuthorDao
import pl.szczeliniak.cookbook.recipe.dto.response.AuthorsResponse
import pl.szczeliniak.cookbook.shared.BaseService
import pl.szczeliniak.cookbook.shared.RequestContext
import pl.szczeliniak.cookbook.shared.TokenType

open class AuthorService(
    private val authorDao: AuthorDao,
    requestContext: RequestContext
) : BaseService(requestContext) {

    fun getAll(name: String? = null): AuthorsResponse {
        requireTokenType(TokenType.ACCESS)
        return AuthorsResponse(authorDao.findAll(AuthorCriteria(name), requestContext.userId()).map { it.name })
    }

}