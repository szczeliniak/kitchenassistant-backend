package pl.szczeliniak.kitchenassistant.recipe

import pl.szczeliniak.kitchenassistant.recipe.db.TagCriteria
import pl.szczeliniak.kitchenassistant.recipe.db.TagDao
import pl.szczeliniak.kitchenassistant.recipe.dto.response.TagsResponse

open class TagService(
    private val tagDao: TagDao,
) {

    fun getAll(criteria: TagCriteria): TagsResponse {
        return TagsResponse(tagDao.findAll(criteria).map { it.name })
    }

}