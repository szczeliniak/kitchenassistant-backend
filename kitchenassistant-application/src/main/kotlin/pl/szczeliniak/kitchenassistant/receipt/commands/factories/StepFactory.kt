package pl.szczeliniak.kitchenassistant.receipt.commands.factories

import pl.szczeliniak.kitchenassistant.exceptions.NotFoundException
import pl.szczeliniak.kitchenassistant.receipt.FileDao
import pl.szczeliniak.kitchenassistant.receipt.Step
import pl.szczeliniak.kitchenassistant.receipt.commands.dto.NewStepDto

open class StepFactory(private val fileDao: FileDao) {

    open fun create(dto: NewStepDto): Step {
        return Step(
            name_ = dto.name,
            description_ = dto.description,
            sequence_ = dto.sequence,
            photos_ = dto.photos.map { fileDao.findById(it) ?: throw NotFoundException("File not found") }
                .toMutableList()
        )
    }

}