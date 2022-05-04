package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component

@Component
class StepMapper {

    fun toDomain(stepEntity: StepEntity): Step {
        return Step(
            stepEntity.id,
            stepEntity.name,
            stepEntity.description,
            stepEntity.sequence,
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
            step.deleted,
            step.createdAt,
            step.modifiedAt
        )
    }

}