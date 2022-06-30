package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.stereotype.Component
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria

@Component
class DayPlanDaoImpl(
    private val dayPlanRepository: DayPlanRepository,
    private val dayPlanMapper: DayPlanMapper
) : DayPlanDao {

    override fun save(dayPlan: DayPlan): DayPlan {
        return dayPlanMapper.toDomain(dayPlanRepository.save(dayPlanMapper.toEntity(dayPlan)))
    }

    override fun findAll(offset: Int, limit: Int, criteria: DayPlanCriteria): Set<DayPlan> {
        return dayPlanRepository.findAll(
            offset,
            limit,
            DayPlanRepository.SearchCriteria(criteria.userId, criteria.archived)
        )
            .map { dayPlanMapper.toDomain(it) }
            .toSet()
    }

    override fun count(criteria: DayPlanCriteria): Long {
        return dayPlanRepository.count(DayPlanRepository.SearchCriteria(criteria.userId, criteria.archived))
    }

    override fun findById(id: Int): DayPlan? {
        return dayPlanRepository.findById(id)?.let { dayPlanMapper.toDomain(it) }
    }

}