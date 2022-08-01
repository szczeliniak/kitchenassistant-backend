package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.AuthorCriteria
import pl.szczeliniak.kitchenassistant.recipe.AuthorDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.AuthorsResponse

class GetAuthorsQuery(private val authorDao: AuthorDao) {

    fun execute(criteria: AuthorCriteria): AuthorsResponse {
        return AuthorsResponse(authorDao.findAll(criteria).map { it.name })
    }

}