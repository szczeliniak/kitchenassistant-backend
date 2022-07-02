package pl.szczeliniak.kitchenassistant.dayplan.queries

import pl.szczeliniak.kitchenassistant.dayplan.DayPlan
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanDetailsDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.SimpleReceiptDto
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.dto.ReceiptResponse

open class DayPlanConverter(private val getReceiptQuery: GetReceiptQuery) {

    open fun map(dayPlan: DayPlan): DayPlanDto {
        return DayPlanDto(
            dayPlan.id,
            dayPlan.date
        )
    }

    open fun mapDetails(dayPlan: DayPlan): DayPlanDetailsDto {
        return DayPlanDetailsDto(
            dayPlan.id,
            dayPlan.date,
            dayPlan.receiptIds.map { mapReceipt(getReceiptQuery.execute(it)) }
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

}