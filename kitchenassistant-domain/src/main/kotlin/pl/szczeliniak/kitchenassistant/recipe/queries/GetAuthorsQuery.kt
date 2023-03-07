package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.AuthorCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.AuthorDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.AuthorsResponse

class GetAuthorsQuery(private val authorDao: AuthorDao) {

    fun execute(criteria: AuthorCriteria): AuthorsResponse {
        return AuthorsResponse(authorDao.findAll(criteria).map { it.name })
    }

}