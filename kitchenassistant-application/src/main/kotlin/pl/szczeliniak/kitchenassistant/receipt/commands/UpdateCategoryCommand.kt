package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateCategoryDto
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class UpdateCategoryCommand(private val categoryDao: CategoryDao) {

    fun execute(id: Int, dto: UpdateCategoryDto): SuccessResponse {
        val category = categoryDao.findById(id) ?: throw NotFoundException("Category not found")
        category.update(dto.name)
        return SuccessResponse(categoryDao.save(category).id)
    }

}