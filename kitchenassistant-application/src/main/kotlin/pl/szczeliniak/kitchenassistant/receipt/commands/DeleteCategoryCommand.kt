package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao

class DeleteCategoryCommand(private val categoryDao: CategoryDao) {

    fun execute(categoryId: Int): SuccessResponse {
        val category = categoryDao.findById(categoryId) ?: throw NotFoundException("Category not found")
        category.markAsDeleted()
        categoryDao.save(category)
        return SuccessResponse(category.id)
    }

}