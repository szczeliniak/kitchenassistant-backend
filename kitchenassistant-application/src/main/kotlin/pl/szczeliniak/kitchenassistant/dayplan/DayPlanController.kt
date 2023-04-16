package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.AddRecipeToDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlansResponse
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.LocalDate

@RestController
@RequestMapping("/dayplans")
@Validated
class DayPlanController(
        private val dayPlanFacade: DayPlanFacade
) {

    @PreAuthorize("@authorizationService.isOwnerOfDayPlan(#id)")
    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): DayPlanResponse {
        return dayPlanFacade.findById(id)
    }

    @PreAuthorize("@authorizationService.isOwner(#userId)")
    @GetMapping
    fun findAll(
            @RequestParam(required = false) userId: Int?,
            @RequestParam(required = false) archived: Boolean?,
            @RequestParam(required = false) page: Long?,
            @RequestParam(required = false) limit: Int?,
            @RequestParam(required = false) since: LocalDate?,
            @RequestParam(required = false) to: LocalDate?
    ): DayPlansResponse {
        return dayPlanFacade.findAll(
                page,
                limit,
                DayPlanCriteria(userId, archived, since = since, to = to)
        )
    }

    @PreAuthorize("@authorizationService.isOwnerOfDayPlan(#id)")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): SuccessResponse {
        return dayPlanFacade.delete(id)
    }

    @PreAuthorize("@authorizationService.isOwnerOfDayPlan(#id)")
    @PutMapping("/{id}/archive/{archive}")
    fun archive(@PathVariable id: Int, @PathVariable archive: Boolean): SuccessResponse {
        return dayPlanFacade.archive(id, archive)
    }

    @PreAuthorize("@authorizationService.isOwnerOfDayPlan(#id)")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody request: UpdateDayPlanDto): SuccessResponse {
        return dayPlanFacade.update(id, request)
    }

    @PreAuthorize("@authorizationService.isOwnerOfRecipe(#request.userId)")
    @PostMapping("/recipes/{recipeId}")
    fun addRecipe(@PathVariable recipeId: Int, @RequestBody request: AddRecipeToDayPlanDto): SuccessResponse {
        return dayPlanFacade.addRecipe(recipeId, request)
    }

    @PreAuthorize("@authorizationService.isOwnerOfDayPlan(#id)")
    @DeleteMapping("/{id}/recipes/{recipeId}")
    fun deleteRecipe(@PathVariable id: Int, @PathVariable recipeId: Int): SuccessResponse {
        return dayPlanFacade.deleteRecipe(id, recipeId)
    }

}