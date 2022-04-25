package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class DeleteCategoryCommand(private val categoryDao: CategoryDao) {

    fun execute(categoryId: Int): SuccessResponse {
        val category = categoryDao.findById(categoryId) ?: throw NotFoundException("Category not found")
        category.markAsDeleted()
        categoryDao.save(category)
        return SuccessResponse(category.id)
    }

}