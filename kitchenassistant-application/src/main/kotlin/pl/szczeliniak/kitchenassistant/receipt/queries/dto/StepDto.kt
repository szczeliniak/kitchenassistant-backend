package pl.szczeliniak.kitchenassistant.receipt.queries.dto

import pl.szczeliniak.kitchenassistant.receipt.Step

data class StepDto(
    val id: Int,
    val name: String,
    val description: String?,
    val sequence: Int?,
    val photos: List<FileDto>
) {

    companion object {
        fun fromDomain(step: Step): StepDto {
            return StepDto(
                step.id,
                step.name,
                step.description,
                step.sequence,
                step.photos.map { FileDto.fromDomain(it) }
            )
        }
    }
}