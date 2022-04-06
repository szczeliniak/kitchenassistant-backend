package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto

class UpdateReceiptCommand(private val receiptDao: ReceiptDao, private val categoryDao: CategoryDao) {

    fun execute(id: Int, dto: UpdateReceiptDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")

        receipt.update(
            dto.name,
            dto.description,
            getCategory(receipt.category, dto.categoryId),
            dto.author,
            dto.source
        )

        return SuccessResponse(receiptDao.save(receipt).id)
    }

    private fun getCategory(category: Category?, categoryId: Int?): Category? {
        if (category != null && categoryId != null && category.id != categoryId) {
            return categoryDao.findById(categoryId) ?: throw NotFoundException("Category not found")
        }
        return category
    }

}