package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.FileDao
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.TagDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

open class ReceiptFactory(
    private val getUserByIdQuery: GetUserByIdQuery,
    private val ingredientFactory: IngredientFactory,
    private val stepFactory: StepFactory,
    private val categoryDao: CategoryDao,
    private val fileDao: FileDao,
    private val tagDao: TagDao,
    private val tagFactory: TagFactory
) {

    open fun create(dto: NewReceiptDto): Receipt {
        getUserByIdQuery.execute(dto.userId)
        return Receipt(
            userId_ = dto.userId,
            name_ = dto.name,
            author_ = dto.author,
            source_ = dto.source,
            category_ = dto.categoryId?.let {
                categoryDao.findById(it) ?: throw NotFoundException("Category not found")
            },
            description_ = dto.description,
            ingredients_ = dto.ingredients.map { ingredientFactory.create(it) }.toMutableList(),
            steps_ = dto.steps.map { stepFactory.create(it) }.toMutableList(),
            photos_ = dto.photos.map { fileDao.findById(it) ?: throw NotFoundException("File not found") }
                .toMutableList(),
            tags_ = dto.tags.map { tagDao.findByName(it, dto.userId) ?: tagDao.save(tagFactory.create(it, dto.userId)) }
                .toMutableList()
        )
    }

}