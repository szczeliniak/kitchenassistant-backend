package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.file.queries.CheckIfFileExistsQuery
import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto
import pl.szczeliniak.kitchenassistant.shared.exceptions.NotFoundException

open class StepFactory(
    private val checkIfFileExistsQuery: CheckIfFileExistsQuery
) {

    open fun create(dto: NewStepDto): Step {
        dto.photos.forEach {
            if (!checkIfFileExistsQuery.execute(it).exists) throw NotFoundException("File not found")
        }
        return Step(
            name_ = dto.name,
            description_ = dto.description,
            sequence_ = dto.sequence
        )
    }

}