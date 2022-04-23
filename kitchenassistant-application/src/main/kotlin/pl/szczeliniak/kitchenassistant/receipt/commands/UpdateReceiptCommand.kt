package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.common.dto.SuccessResponse
import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.Category
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.ReceiptDao
import pl.szczeliniak.kitchenassistant.receipt.TagDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.TagFactory

class UpdateReceiptCommand(
    private val receiptDao: ReceiptDao,
    private val categoryDao: CategoryDao,
    private val tagDao: TagDao,
    private val tagFactory: TagFactory
) {

    fun execute(id: Int, dto: UpdateReceiptDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")

        receipt.update(
            dto.name,
            dto.description,
            getCategory(receipt.category, dto.categoryId),
            dto.author,
            dto.source,
            dto.tags.map {
                receipt.getTagByName(it) ?: tagDao.findByName(it, receipt.userId) ?: tagDao.save(
                    tagFactory.create(it, receipt.userId)
                )
            }
        )

        tagDao.saveAll(receipt.tags)
        return SuccessResponse(receiptDao.save(receipt).id)
    }

    private fun getCategory(category: Category?, categoryId: Int?): Category? {
        if (category != null && categoryId != null && category.id == categoryId) {
            return category
        }
        return categoryId?.let { categoryDao.findById(it) ?: throw NotFoundException("Category not found") }
    }

}