package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.AuthorCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorDao
import pl.szczeliniak.kitchenassistant.recipe.dto.response.AuthorsResponse

open class AuthorService(
    private val authorDao: AuthorDao,
) {

    fun getAll(criteria: AuthorCriteria): AuthorsResponse {
        return AuthorsResponse(authorDao.findAll(criteria).map { it.name })
    }

}