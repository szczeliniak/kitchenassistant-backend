package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.user.queries.GetUserByIdQuery

open class ReceiptFactory(
    private val getUserByIdQuery: GetUserByIdQuery,
    private val ingredientFactory: IngredientFactory,
    private val stepFactory: StepFactory
) {

    open fun create(dto: NewReceiptDto): Receipt {
        getUserByIdQuery.execute(dto.userId)
        return Receipt(
            userId_ = dto.userId,
            name_ = dto.name,
            author_ = dto.author,
            source_ = dto.source,
            description_ = dto.description,
            ingredients_ = dto.ingredients.map { ingredientFactory.create(it) }.toMutableList(),
            steps_ = dto.steps.map { stepFactory.create(it) }.toMutableList()
        )
    }

}