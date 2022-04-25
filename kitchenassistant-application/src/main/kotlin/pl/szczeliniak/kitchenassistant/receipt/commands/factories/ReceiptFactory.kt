package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.file.queries.CheckIfFileExistsQuery
import pl.szczeliniak.kitchenassistant.receipt.CategoryDao
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.TagDao
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

open class ReceiptFactory(
    private val getUserByIdQuery: GetUserByIdQuery,
    private val ingredientFactory: IngredientFactory,
    private val stepFactory: StepFactory,
    private val categoryDao: CategoryDao,
    private val photoFactory: PhotoFactory,
    private val tagDao: TagDao,
    private val tagFactory: TagFactory,
    private val checkIfFileExistsQuery: CheckIfFileExistsQuery
) {

    open fun create(dto: NewReceiptDto): Receipt {
        getUserByIdQuery.execute(dto.userId)
        dto.photos.forEach {
            if (!checkIfFileExistsQuery.execute(it).exists) throw NotFoundException("File not found")
        }

        return Receipt(
            userId_ = dto.userId,
            name_ = dto.name,
            author_ = dto.author,
            source_ = dto.source,
            category_ = dto.categoryId?.let {
                categoryDao.findById(it) ?: throw NotFoundException("Category not found")
            },
            description_ = dto.description,
            ingredients_ = dto.ingredients.map { ingredientFactory.create(it) }.toMutableSet(),
            steps_ = dto.steps.map { stepFactory.create(it) }.toMutableSet(),
            photos_ = dto.photos.map { photoFactory.create(it) }.toMutableSet(),
            tags_ = dto.tags.map { tagDao.findByName(it, dto.userId) ?: tagDao.save(tagFactory.create(it, dto.userId)) }
                .toMutableSet()
        )
    }

}