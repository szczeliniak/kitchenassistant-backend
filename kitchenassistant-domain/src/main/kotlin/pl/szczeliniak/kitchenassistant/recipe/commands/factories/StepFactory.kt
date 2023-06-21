package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewStepRequest
import pl.szczeliniak.kitchenassistant.recipe.db.Step

open class StepFactory {

    open fun create(request: NewStepRequest): Step {
        return Step(0, request.description, request.photoName, request.sequence)
    }

}