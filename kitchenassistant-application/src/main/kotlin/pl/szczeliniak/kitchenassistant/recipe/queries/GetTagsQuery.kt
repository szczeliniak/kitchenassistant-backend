package pl.szczeliniak.kitchenassistant.recipe.queries

import pl.szczeliniak.kitchenassistant.recipe.TagCriteria
import pl.szczeliniak.kitchenassistant.recipe.TagDao
import pl.szczeliniak.kitchenassistant.recipe.queries.dto.TagsResponse

class GetTagsQuery(private val tagDao: TagDao) {

    fun execute(criteria: TagCriteria): TagsResponse {
        return TagsResponse(tagDao.findAll(criteria).map { it.name })
    }

}