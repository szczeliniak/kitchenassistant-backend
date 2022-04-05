package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewCategoryDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.CategoryFactory

class AddCategoryCommand(
    private val categoryDao: CategoryDao,
    private val categoryFactory: CategoryFactory
) {

    fun execute(dto: NewCategoryDto): SuccessResponse {
        return SuccessResponse(categoryDao.save(categoryFactory.create(dto)).id)
    }

}