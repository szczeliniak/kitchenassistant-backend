package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.AddNewReceiptDto
import pl.szczeliniak.kitchenassistant.user.UserDao

open class ReceiptFactory(
    private val userDao: UserDao,
    private val ingredientFactory: IngredientFactory,
    private val stepFactory: StepFactory
) {

    open fun create(dto: AddNewReceiptDto): Receipt {
        return Receipt(
            user_ = userDao.findById(dto.userId) ?: throw NotFoundException("User not found"),
            name_ = dto.name,
            author_ = dto.author,
            source_ = dto.source,
            description_ = dto.description,
            ingredients_ = dto.ingredients.map { ingredientFactory.create(it) },
            steps_ = dto.steps.map { stepFactory.create(it) }
        )
    }

}