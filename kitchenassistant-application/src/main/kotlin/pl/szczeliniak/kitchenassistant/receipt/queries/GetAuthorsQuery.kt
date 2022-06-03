package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.AuthorCriteria
import pl.szczeliniak.kitchenassistant.receipt.AuthorDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.AuthorsResponse

class GetAuthorsQuery(private val authorDao: AuthorDao) {

    fun execute(criteria: AuthorCriteria): AuthorsResponse {
        return AuthorsResponse(authorDao.findAll(criteria).map { it.name })
    }

}