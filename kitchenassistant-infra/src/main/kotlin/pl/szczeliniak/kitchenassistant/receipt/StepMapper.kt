package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class StepMapper(private val photoMapper: PhotoMapper) {

    fun toDomain(stepEntity: StepEntity): Step {
        return Step(
            stepEntity.id,
            stepEntity.name,
            stepEntity.description,
            stepEntity.sequence,
            stepEntity.photos.map { photoMapper.toDomain(it) }.toMutableSet(),
            stepEntity.deleted,
            stepEntity.createdAt,
            stepEntity.modifiedAt
        )
    }

    fun toEntity(step: Step): StepEntity {
        return StepEntity(
            step.id,
            step.name,
            step.description,
            step.sequence,
            step.photos.map { photoMapper.toEntity(it) }.toMutableSet(),
            step.deleted,
            step.createdAt,
            step.modifiedAt
        )
    }

}