package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewStepRequest

open class StepFactory {

    open fun create(request: NewStepRequest): Step {
        return Step(0, request.name, request.description, request.sequence)
    }

}