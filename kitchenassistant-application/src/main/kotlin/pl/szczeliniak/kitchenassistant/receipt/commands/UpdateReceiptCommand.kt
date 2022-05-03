package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.PhotoFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.TagFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class UpdateReceiptCommand(
    private val receiptDao: ReceiptDao,
    private val categoryDao: CategoryDao,
    private val tagDao: TagDao,
    private val tagFactory: TagFactory,
    private val photoFactory: PhotoFactory,
    private val photoDao: PhotoDao
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
            },
            dto.photos.map { receipt.getPhotoByFileId(it) ?: photoDao.save(photoFactory.create(it)) }
        )

        tagDao.saveAll(receipt.tags)
        photoDao.saveAll(receipt.photos)
        return SuccessResponse(receiptDao.save(receipt).id)
    }

    private fun getCategory(category: Category?, categoryId: Int?): Category? {
        if (category != null && categoryId != null && category.id == categoryId) {
            return category
        }
        return categoryId?.let { categoryDao.findById(it) ?: throw NotFoundException("Category not found") }
    }

}