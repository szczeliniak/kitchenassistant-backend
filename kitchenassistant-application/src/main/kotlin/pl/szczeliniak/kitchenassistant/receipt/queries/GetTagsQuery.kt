package pl.szczeliniak.kitchenassistant.receipt.queries

import pl.szczeliniak.kitchenassistant.receipt.TagCriteria
import pl.szczeliniak.kitchenassistant.receipt.TagDao
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.TagsResponse

class GetTagsQuery(private val tagDao: TagDao) {

    fun execute(criteria: TagCriteria): TagsResponse {
        return TagsResponse(tagDao.findAll(criteria).map { it.name })
    }

}