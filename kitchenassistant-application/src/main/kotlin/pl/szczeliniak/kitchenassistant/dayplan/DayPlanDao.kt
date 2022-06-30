package pl.szczeliniak.kitchenassistant.dayplan

import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria

interface DayPlanDao {

    fun save(dayPlan: DayPlan): DayPlan

    fun findAll(offset: Int, limit: Int, criteria: DayPlanCriteria): Set<DayPlan>

    fun count(criteria: DayPlanCriteria): Long

    fun findById(id: Int): DayPlan?

}