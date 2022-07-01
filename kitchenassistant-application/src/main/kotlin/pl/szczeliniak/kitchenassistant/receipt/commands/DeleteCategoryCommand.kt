package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptCriteria
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class DeleteCategoryCommand(private val categoryDao: CategoryDao, private val receiptDao: ReceiptDao) {

    fun execute(categoryId: Int): SuccessResponse {
        val category = categoryDao.findById(categoryId) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
        if (category.deleted) {
            throw KitchenAssistantException(ErrorCode.CATEGORY_ALREADY_REMOVED)
        }
        category.deleted = true

        val receipts = receiptDao.findAll(ReceiptCriteria(category.userId, category.id))
        receipts.forEach { it.category = null }
        receiptDao.save(receipts)

        categoryDao.save(category)
        return SuccessResponse(category.id)
    }

}