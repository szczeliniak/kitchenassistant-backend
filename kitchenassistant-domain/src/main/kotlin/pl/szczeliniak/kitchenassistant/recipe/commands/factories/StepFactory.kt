package pl.szczeliniak.kitchenassistant.recipe.commands.factories

import pl.szczeliniak.kitchenassistant.recipe.db.Step
import pl.szczeliniak.kitchenassistant.recipe.commands.dto.NewStepDto

open class StepFactory {

    open fun create(dto: NewStepDto): Step {
        return Step(0, dto.name, dto.description, dto.sequence)
    }

}