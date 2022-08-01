package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.NewDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanRecipesResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlansResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.LocalDate
import javax.validation.Valid

@RestController
@RequestMapping("/dayplans")
@Validated
class DayPlanController(
    private val dayPlanFacade: DayPlanFacade
) {

    @GetMapping("/{id}")
    fun getDayPlan(@PathVariable id: Int): DayPlanResponse {
        return dayPlanFacade.getDayPlan(id)
    }

    @GetMapping
    fun getDayPlans(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) archived: Boolean?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = false) since: LocalDate?,
        @RequestParam(required = false) to: LocalDate?,
        @RequestParam(required = false) name: String?
    ): DayPlansResponse {
        return dayPlanFacade.getDayPlans(
            page,
            limit,
            DayPlanCriteria(userId, archived, since = since, to = to, name = name)
        )
    }

    @PostMapping
    fun addDayPlan(@Valid @RequestBody dto: NewDayPlanDto): SuccessResponse {
        return dayPlanFacade.addDayPlan(dto)
    }

    @PutMapping("/{id}")
    fun updateDayPlan(@PathVariable id: Int, @Valid @RequestBody dto: UpdateDayPlanDto): SuccessResponse {
        return dayPlanFacade.updateDayPlan(id, dto)
    }

    @DeleteMapping("/{id}")
    fun deleteDayPlan(@PathVariable id: Int): SuccessResponse {
        return dayPlanFacade.deleteDayPlan(id)
    }

    @PutMapping("/{id}/archive/{archive}")
    fun archiveDayPlan(@PathVariable id: Int, @PathVariable archive: Boolean): SuccessResponse {
        return dayPlanFacade.archiveDayPlan(id, archive)
    }

    @PostMapping("/{id}/recipes/{recipeId}")
    fun addRecipeToDayPlan(@PathVariable id: Int, @PathVariable recipeId: Int): SuccessResponse {
        return dayPlanFacade.addRecipeToDayPlan(id, recipeId)
    }

    @DeleteMapping("/{id}/recipes/{recipeId}")
    fun deleteRecipeFromDayPlan(@PathVariable id: Int, @PathVariable recipeId: Int): SuccessResponse {
        return dayPlanFacade.deleteRecipeFromDayPlan(id, recipeId)
    }

    @GetMapping("/{id}/recipes")
    fun getRecipesByDayPlanId(@PathVariable id: Int): DayPlanRecipesResponse {
        return dayPlanFacade.getRecipesByDayPlanId(id)
    }

}