package pl.szczeliniak.kitchenassistant.dayplan.queries

import pl.szczeliniak.kitchenassistant.dayplan.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.*
import pl.szczeliniak.kitchenassistant.receipt.ReceiptFacade
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.IngredientDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.IngredientGroupDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptDetailsDto
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse

open class DayPlanConverter(private val receiptFacade: ReceiptFacade) {

    open fun map(dayPlan: DayPlan): DayPlanDto {
        return DayPlanDto(
            dayPlan.id,
            dayPlan.name,
            dayPlan.date
        )
    }

    open fun mapDetails(dayPlan: DayPlan): DayPlanDetailsDto {
        return DayPlanDetailsDto(
            dayPlan.id,
            dayPlan.name,
            dayPlan.description,
            dayPlan.date,
            dayPlan.receiptIds.map { mapReceipt(receiptFacade.getReceipt(it)) },
            dayPlan.automaticArchiving
        )
    }

    private fun mapReceipt(response: ReceiptResponse): SimpleReceiptDto {
        return SimpleReceiptDto(
            response.receipt.id,
            response.receipt.name,
            response.receipt.author,
            response.receipt.category?.name
        )
    }

    fun map(receiptDto: ReceiptDetailsDto): DayPlanReceiptDto {
        return DayPlanReceiptDto(receiptDto.name, receiptDto.ingredientGroups.map { map(it) }, receiptDto.author)
    }

    private fun map(ingredientGroupDto: IngredientGroupDto): DayPlanIngredientGroupDto {
        return DayPlanIngredientGroupDto(ingredientGroupDto.name, ingredientGroupDto.ingredients.map { map(it) })
    }

    private fun map(ingredientDto: IngredientDto): DayPlanIngredientDto {
        return DayPlanIngredientDto(ingredientDto.name, ingredientDto.quantity)
    }

}