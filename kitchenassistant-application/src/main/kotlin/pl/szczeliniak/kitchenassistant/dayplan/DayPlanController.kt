package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dayplan.db.Sort
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

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): DayPlanResponse {
        return dayPlanService.findById(id)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = false) since: LocalDate?,
        @RequestParam(required = false) to: LocalDate?,
        @RequestParam(required = true, defaultValue = "ASC") sort: Sort
    ): DayPlansResponse {
        return dayPlanService.findAll(
            page,
            limit,
            DayPlanCriteria(since = since, to = to),
            sort
        )
    }

    @Transactional
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): SuccessResponse {
        return dayPlanService.delete(id)
    }

    @Transactional
    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Valid @RequestBody request: UpdateDayPlanRequest): SuccessResponse {
        return dayPlanService.update(id, request)
    }

    @Transactional
    @PutMapping("/{id}/recipes/{recipeId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}/{isChecked}")
    fun check(
        @PathVariable id: Int,
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int,
        @PathVariable isChecked: Boolean
    ): SuccessResponse {
        return dayPlanService.check(id, recipeId, ingredientGroupId, ingredientId, isChecked)
    }

    @Transactional
    @PostMapping
    fun addRecipe(@Valid @RequestBody request: AddRecipeToDayPlanRequest): SuccessResponse {
        return dayPlanService.addRecipe(request)
    }

    @Transactional
    @DeleteMapping("/{id}/recipes/{recipeId}")
    fun deleteRecipe(@PathVariable id: Int, @PathVariable recipeId: Int): SuccessResponse {
        return dayPlanService.deleteRecipe(id, recipeId)
    }

}