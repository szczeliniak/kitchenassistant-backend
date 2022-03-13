package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewReceiptDto
import pl.szczeliniak.kitchenassistant.user.queries.GetUserQuery

open class ReceiptFactory(
    private val getUserQuery: GetUserQuery,
    private val ingredientFactory: IngredientFactory,
    private val stepFactory: StepFactory
) {

    open fun create(dto: NewReceiptDto): Receipt {
        getUserQuery.execute(dto.userId)
        return Receipt(
            userId_ = dto.userId,
            name_ = dto.name,
            author_ = dto.author,
            source_ = dto.source,
            description_ = dto.description,
            ingredients_ = dto.ingredients.map { ingredientFactory.create(it) },
            steps_ = dto.steps.map { stepFactory.create(it) }
        )
    }

}