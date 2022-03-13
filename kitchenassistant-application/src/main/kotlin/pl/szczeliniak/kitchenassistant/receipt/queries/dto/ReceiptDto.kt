package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.Receipt
import pl.szczeliniak.kitchenassistant.user.queries.dto.UserDto

data class ReceiptDto(
    val id: Int,
    val user: UserDto,
    val name: String,
    val description: String?,
    val author: String?,
    val source: String?,
    val ingredients: List<IngredientDto>,
    val steps: List<StepDto>,
) {
    companion object {
        fun fromDomain(receipt: Receipt): ReceiptDto {
            return ReceiptDto(
                receipt.id,
                UserDto.fromDomain(receipt.user),
                receipt.name,
                receipt.description,
                receipt.author,
                receipt.source,
                receipt.ingredients.map { IngredientDto.fromDomain(it) },
                receipt.steps.map { StepDto.fromDomain(it) }
            )
        }
    }
}