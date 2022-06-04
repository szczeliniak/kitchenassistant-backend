package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.*
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

open class ReceiptFactory(
    private val getUserByIdQuery: GetUserByIdQuery,
    private val ingredientFactory: IngredientFactory,
    private val stepFactory: StepFactory,
    private val categoryDao: CategoryDao,
    private val tagDao: TagDao,
    private val tagFactory: TagFactory,
    private val authorDao: AuthorDao,
    private val authorFactory: AuthorFactory,
    private val photoDao: PhotoDao
) {

    open fun create(dto: NewReceiptDto): Receipt {
        getUserByIdQuery.execute(dto.userId)
        return Receipt(
            userId_ = dto.userId,
            name_ = dto.name,
            author_ = dto.author?.let {
                authorDao.findByName(it, dto.userId) ?: authorDao.save(
                    authorFactory.create(
                        it,
                        dto.userId
                    )
                )
            },
            source_ = dto.source,
            category_ = dto.categoryId?.let {
                categoryDao.findById(it) ?: throw NotFoundException("Category with id $it not found")
            },
            description_ = dto.description,
            ingredients_ = dto.ingredients.map { ingredientFactory.create(it) }.toMutableSet(),
            steps_ = dto.steps.map { stepFactory.create(it) }.toMutableSet(),
            photos_ = dto.photos.map { photoDao.findById(it) ?: throw NotFoundException("Photo with id $it not found") }
                .toMutableSet(),
            tags_ = dto.tags.map { tagDao.findByName(it, dto.userId) ?: tagDao.save(tagFactory.create(it, dto.userId)) }
                .toMutableSet()
        )
    }

}