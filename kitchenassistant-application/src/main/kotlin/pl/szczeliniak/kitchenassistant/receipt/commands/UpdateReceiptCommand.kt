package pl.szczeliniak.kitchenassistant.receipt.commands

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.UpdateReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.AuthorFactory
import pl.szczeliniak.kitchenassistant.receipt.commands.factories.TagFactory
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

class UpdateReceiptCommand(
    private val receiptDao: ReceiptDao,
    private val categoryDao: CategoryDao,
    private val tagDao: TagDao,
    private val tagFactory: TagFactory,
    private val photoDao: PhotoDao,
    private val authorFactory: AuthorFactory,
    private val authorDao: AuthorDao
) {

    fun execute(id: Int, dto: UpdateReceiptDto): SuccessResponse {
        val receipt = receiptDao.findById(id) ?: throw NotFoundException("Receipt not found")

        receipt.update(
            dto.name,
            dto.description,
            getCategory(receipt.category, dto.categoryId),
            dto.author?.let {
                authorDao.findByName(it, receipt.userId) ?: authorDao.save(
                    authorFactory.create(
                        it,
                        receipt.userId
                    )
                )
            },
            dto.source,
            dto.tags.map {
                receipt.getTagByName(it) ?: tagDao.findByName(it, receipt.userId) ?: tagDao.save(
                    tagFactory.create(it, receipt.userId)
                )
            },
            dto.photos.map {
                receipt.getPhotoById(it) ?: photoDao.findById(it)
                ?: throw NotFoundException("Photo with id $it not found")
            }
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