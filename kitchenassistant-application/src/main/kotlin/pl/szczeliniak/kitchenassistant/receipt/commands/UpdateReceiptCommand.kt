package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.AuthorFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.TagFactory
import pl.szczeliniak.kitchenassistant.shared.ErrorCode
import pl.szczeliniak.kitchenassistant.shared.KitchenAssistantException
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse

class UpdateReceiptCommand(
    private val receiptDao: ReceiptDao,
    private val categoryDao: CategoryDao,
    private val tagDao: TagDao,
    private val tagFactory: TagFactory,
    private val authorFactory: AuthorFactory,
    private val authorDao: AuthorDao
) {

    fun execute(id: Int, dto: UpdateReceiptDto): SuccessResponse {
        val receipt =
            receiptDao.findById(id) ?: throw KitchenAssistantException(ErrorCode.RECEIPT_NOT_FOUND)

        receipt.name = dto.name
        receipt.description = dto.description
        receipt.category = getCategory(receipt.category, dto.categoryId)
        receipt.source = dto.source
        receipt.author = dto.author?.let {
            authorDao.findByName(it, receipt.userId) ?: authorDao.save(
                authorFactory.create(
                    it,
                    receipt.userId
                )
            )
        }
        receipt.tags = dto.tags.map {
            receipt.tags.firstOrNull { tag -> it == tag.name } ?: tagDao.findByName(it, receipt.userId) ?: tagDao.save(
                tagFactory.create(it, receipt.userId)
            )
        }.toMutableSet()

        return SuccessResponse(receiptDao.save(receipt).id)
    }

    private fun getCategory(category: Category?, categoryId: Int?): Category? {
        if (category != null && categoryId != null && category.id == categoryId) {
            return category
        }
        return categoryId?.let {
            categoryDao.findById(it) ?: throw KitchenAssistantException(ErrorCode.CATEGORY_NOT_FOUND)
        }
    }

}