package pl.szczeliniak.kitchenassistant.recipe.dto.request

import javax.validation.Valid
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.Size

data class UpdateStepsRequest(var stepGroups: List<StepGroup>) {

    data class StepGroup(
        @field:Size(min = 1, max = 100) var name: String? = null,
        @Min(0) @Max(30) val steps: List<@Valid Step> = listOf()
    ) {
        data class Step(
            @field:Size(max = 1000) var description: String,
            var sequence: Int? = null
        )
    }

}