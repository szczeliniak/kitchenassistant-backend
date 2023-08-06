package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.JacksonConfiguration
import pl.szczeliniak.kitchenassistant.dayplan.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.dto.request.AddRecipeToDayPlanRequest
import pl.szczeliniak.kitchenassistant.dayplan.dto.request.UpdateDayPlanRequest
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.dto.response.DayPlansResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.LocalDate
import javax.transaction.Transactional
import javax.validation.Valid

@RestController
@RequestMapping("/dayplans")
@Validated
class DayPlanController(
    private val dayPlanService: DayPlanService,
) {

    @GetMapping("/{date}")
    fun findByDate(@PathVariable @DateTimeFormat(pattern = JacksonConfiguration.DATE_FORMAT) date: LocalDate): DayPlanResponse {
        return dayPlanService.findByDate(date)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = false) since: LocalDate?,
        @RequestParam(required = false) to: LocalDate?
    ): DayPlansResponse {
        return dayPlanService.findAll(
            page,
            limit,
            DayPlanCriteria(since = since, to = to)
        )
    }

    @Transactional
    @DeleteMapping("/{date}")
    fun delete(@PathVariable @DateTimeFormat(pattern = JacksonConfiguration.DATE_FORMAT) date: LocalDate): SuccessResponse {
        return dayPlanService.delete(date)
    }

    @Transactional
    @PutMapping("/{date}")
    fun update(
        @PathVariable @DateTimeFormat(pattern = JacksonConfiguration.DATE_FORMAT) date: LocalDate,
        @Valid @RequestBody request: UpdateDayPlanRequest
    ): SuccessResponse {
        return dayPlanService.update(date, request)
    }

    @Transactional
    @PostMapping
    fun addRecipe(@Valid @RequestBody request: AddRecipeToDayPlanRequest): SuccessResponse {
        return dayPlanService.addRecipe(request)
    }

    @Transactional
    @DeleteMapping("/{id}/recipes/{recipeId}")
    fun deleteRecipe(
        @PathVariable @DateTimeFormat(pattern = JacksonConfiguration.DATE_FORMAT) date: LocalDate,
        @PathVariable recipeId: Int
    ): SuccessResponse {
        return dayPlanService.deleteRecipe(date, recipeId)
    }

}