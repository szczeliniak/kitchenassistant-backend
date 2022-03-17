package pl.szczeliniak.kitchenassistant.receipt

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.receipt.persistance.StepRepository

@Component
class StepDaoImpl(
    private val stepRepository: StepRepository,
    private val stepMapper: StepMapper
) : StepDao {

    override fun save(step: Step): Step {
        return stepMapper.toDomain(stepRepository.save(stepMapper.toEntity(step)))
    }

}