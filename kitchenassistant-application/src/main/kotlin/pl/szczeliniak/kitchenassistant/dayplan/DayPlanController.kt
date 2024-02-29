package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import pl.szczeliniak.kitchenassistant.dayplan.db.Sort
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
    fun findByDate(@PathVariable date: LocalDate): DayPlanResponse {
        return dayPlanService.findByDate(date)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = false) recipeId: Int?,
        @RequestParam(required = false) since: LocalDate?,
        @RequestParam(required = false) to: LocalDate?,
        @RequestParam(required = true, defaultValue = "ASC") sort: Sort
    ): DayPlansResponse {
        return dayPlanService.findAll(page, limit, sort, recipeId, since, to)
    }

    @Transactional
    @DeleteMapping("/{date}")
    fun delete(@PathVariable date: LocalDate): SuccessResponse {
        return dayPlanService.delete(date)
    }

    @Transactional
    @PutMapping("/{date}")
    fun update(@PathVariable date: LocalDate, @Valid @RequestBody request: UpdateDayPlanRequest): SuccessResponse {
        return dayPlanService.update(date, request)
    }

    @Transactional
    @PutMapping("/{date}/recipes/{recipeId}/ingredientGroups/{ingredientGroupId}/ingredients/{ingredientId}/{isChecked}")
    fun checkIngredient(
        @PathVariable date: LocalDate,
        @PathVariable recipeId: Int,
        @PathVariable ingredientGroupId: Int,
        @PathVariable ingredientId: Int,
        @PathVariable isChecked: Boolean
    ): SuccessResponse {
        return dayPlanService.checkIngredient(date, recipeId, ingredientGroupId, ingredientId, isChecked)
    }

    @Transactional
    @PostMapping
    fun addRecipe(@Valid @RequestBody request: AddRecipeToDayPlanRequest): SuccessResponse {
        return dayPlanService.addRecipe(request)
    }

    @Transactional
    @DeleteMapping("/{date}/recipes/{recipeId}")
    fun deleteRecipe(@PathVariable date: LocalDate, @PathVariable recipeId: Int): SuccessResponse {
        return dayPlanService.deleteRecipe(date, recipeId)
    }

}