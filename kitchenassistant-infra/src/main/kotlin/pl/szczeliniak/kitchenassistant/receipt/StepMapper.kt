package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.StepEntity

@Component
class StepMapper {

    fun toDomain(stepEntity: StepEntity): Step {
        return Step(
            stepEntity.id,
            stepEntity.title,
            stepEntity.description,
            stepEntity.sequence,
            stepEntity.createdAt,
            stepEntity.modifiedAt
        )
    }

    fun toEntity(step: Step): StepEntity {
        return StepEntity(step.id, step.title, step.description, step.sequence, step.createdAt, step.modifiedAt)
    }

}