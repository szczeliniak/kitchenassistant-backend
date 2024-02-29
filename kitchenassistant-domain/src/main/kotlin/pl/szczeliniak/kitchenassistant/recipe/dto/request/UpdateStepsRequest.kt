package pl.szczeliniak.kitchenassistant.recipe.dto.request

import javax.validation.constraints.Size

data class UpdateStepsRequest(var steps: List<Step>) {
    data class Step(
        @field:Size(max = 1000) var description: String,
        var sequence: Int? = null
    )

}