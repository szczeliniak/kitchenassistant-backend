package pl.szczeliniak.kitchenassistant.receipt.commands.dto

import pl.szczeliniak.kitchenassistant.receipt.Step

data class NewStepDto(
    var title: String = "",
    var description: String? = null,
    var sequence: Int? = null
) {

    companion object {
        fun fromDomain(step: Step): NewStepDto {
            return NewStepDto(
                step.title,
                step.description,
                step.sequence
            )
        }
    }
}