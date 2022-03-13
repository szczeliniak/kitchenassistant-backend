package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto

open class StepFactory {

    open fun create(dto: NewStepDto): Step {
        return Step(title_ = dto.title, description_ = dto.description, sequence_ = dto.sequence)
    }

}