package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteCategoryCommand(private val categoryDao: CategoryDao) {

    fun execute(categoryId: Int): SuccessResponse {
        val category = categoryDao.findById(categoryId) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
        category.markAsDeleted()
        categoryDao.save(category)
        return SuccessResponse(category.id)
    }

}