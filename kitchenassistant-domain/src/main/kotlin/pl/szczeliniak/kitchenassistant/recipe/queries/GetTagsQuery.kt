package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.db.TagCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.TagDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.TagsResponse

class GetTagsQuery(private val tagDao: TagDao) {

    fun execute(criteria: TagCriteria): TagsResponse {
        return TagsResponse(tagDao.findAll(criteria).map { it.name })
    }

}