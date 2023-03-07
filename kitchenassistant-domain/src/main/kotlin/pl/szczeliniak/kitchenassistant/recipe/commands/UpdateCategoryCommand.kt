package pl.szczeliniak.kitchenassistant.recipe.commands

import pl.szczeliniak.kitchenassistant.recipe.db.CategoryDao
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.UpdateCategoryDto
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateCategoryCommand(private val categoryDao: CategoryDao) {

    fun execute(id: Int, dto: UpdateCategoryDto): SuccessResponse {
        val category = categoryDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
        category.name = dto.name
        category.sequence = dto.sequence
        return SuccessResponse(categoryDao.save(category).id)
    }

}