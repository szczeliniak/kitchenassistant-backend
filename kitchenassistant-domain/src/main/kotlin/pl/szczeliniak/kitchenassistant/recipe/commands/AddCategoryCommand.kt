package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewCategoryRequest
import pl.szczeliniak.kitchenassistant.recipe.commands.factories.CategoryFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class AddCategoryCommand(
        private val categoryDao: CategoryDao,
        private val categoryFactory: CategoryFactory
) {

    fun execute(request: NewCategoryRequest): SuccessResponse {
        return SuccessResponse(categoryDao.save(categoryFactory.create(request)).id)
    }

}