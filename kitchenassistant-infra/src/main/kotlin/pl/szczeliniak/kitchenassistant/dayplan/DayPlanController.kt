package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlanQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlansQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlansResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import javax.validation.Valid

@RestController
@RequestMapping("/dayplans")
@Validated
class DayPlanController(
    private val getDayPlanQuery: GetDayPlanQuery,
    private val getDayPlansQuery: GetDayPlansQuery,
    private val addDayPlanCommand: AddDayPlanCommand,
    private val updateDayPlanCommand: UpdateDayPlanCommand,
    private val addReceiptToDayPlanCommand: AddReceiptToDayPlanCommand,
    private val deleteReceiptFromDayPlanCommand: DeleteReceiptFromDayPlanCommand,
    private val deleteDayPlanCommand: DeleteDayPlanCommand,
    private val archiveDayPlanCommand: ArchiveDayPlanCommand
) {

    @GetMapping("/{id}")
    fun getDayPlan(@PathVariable id: Int): DayPlanResponse {
        return getDayPlanQuery.execute(id)
    }

    @GetMapping
    fun getDayPlans(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) archived: Boolean?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
    ): DayPlansResponse {
        return getDayPlansQuery.execute(page, limit, DayPlanCriteria(userId, archived))
    }

    @PostMapping
    fun addDayPlan(@Valid @RequestBody dto: NewDayPlanDto): SuccessResponse {
        return addDayPlanCommand.execute(dto)
    }

    @PutMapping("/{id}")
    fun updateDayPlan(@PathVariable id: Int, @Valid @RequestBody dto: UpdateDayPlanDto): SuccessResponse {
        return updateDayPlanCommand.execute(id, dto)
    }

    @DeleteMapping("/{id}")
    fun deleteDayPlan(@PathVariable id: Int): SuccessResponse {
        return deleteDayPlanCommand.execute(id)
    }

    @PutMapping("/{id}/archive/{archive}")
    fun archiveDayPlan(@PathVariable id: Int, @PathVariable archive: Boolean): SuccessResponse {
        return archiveDayPlanCommand.execute(id, archive)
    }

    @PostMapping("/{id}/receipts/{receiptId}")
    fun addReceiptToDayPlan(@PathVariable id: Int, @PathVariable receiptId: Int): SuccessResponse {
        return addReceiptToDayPlanCommand.execute(id, receiptId)
    }

    @DeleteMapping("/{id}/receipts/{receiptId}")
    fun deleteReceiptFromDayPlan(@PathVariable id: Int, @PathVariable receiptId: Int): SuccessResponse {
        return deleteReceiptFromDayPlanCommand.execute(id, receiptId)
    }

}