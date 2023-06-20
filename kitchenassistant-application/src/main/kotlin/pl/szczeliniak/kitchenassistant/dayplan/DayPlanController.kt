package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.AddRecipeToDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.commands.dto.UpdateDayPlanDto
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanResponse
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlansResponse
import pl.szczeliniak.kitchenassistant.security.AuthorizationService
import pl.szczeliniak.kitchenassistant.shared.dtos.SuccessResponse
import java.time.LocalDate

@RestController
@RequestMapping("/dayplans")
@Validated
class DayPlanController(
    private val dayPlanFacade: DayPlanFacade,
    private val authorizationService: AuthorizationService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): DayPlanResponse {
        authorizationService.checkIsOwnerOfDayPlan(id)
        return dayPlanFacade.findById(id)
    }

    @GetMapping
    fun findAll(
        @RequestParam(required = false) userId: Int?,
        @RequestParam(required = false) archived: Boolean?,
        @RequestParam(required = false) page: Long?,
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = false) since: LocalDate?,
        @RequestParam(required = false) to: LocalDate?
    ): DayPlansResponse {
        authorizationService.checkIsOwner(userId)
        return dayPlanFacade.findAll(
            page,
            limit,
            DayPlanCriteria(userId, archived, since = since, to = to)
        )
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): SuccessResponse {
        authorizationService.checkIsOwnerOfDayPlan(id)
        return dayPlanFacade.delete(id)
    }

    @PutMapping("/{id}/archive/{archive}")
    fun archive(@PathVariable id: Int, @PathVariable archive: Boolean): SuccessResponse {
        authorizationService.checkIsOwnerOfDayPlan(id)
        return dayPlanFacade.archive(id, archive)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody request: UpdateDayPlanDto): SuccessResponse {
        authorizationService.checkIsOwnerOfDayPlan(id)
        return dayPlanFacade.update(id, request)
    }

    @PostMapping
    fun addRecipe(@RequestBody request: AddRecipeToDayPlanDto): SuccessResponse {
        authorizationService.checkIsOwnerOfRecipe(request.recipeId)
        return dayPlanFacade.addRecipe(request)
    }

    @DeleteMapping("/{id}/recipes/{recipeId}")
    fun deleteRecipe(@PathVariable id: Int, @PathVariable recipeId: Int): SuccessResponse {
        authorizationService.checkIsOwnerOfDayPlan(id)
        return dayPlanFacade.deleteRecipe(id, recipeId)
    }

}