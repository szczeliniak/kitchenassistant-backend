package pl.szczeliniak.kitchenassistant.dayplan.db

import pl.szczeliniak.kitchenassistant.dayplan.dto.DayPlanCriteria
import java.time.LocalDate

interface DayPlanDao {

    fun save(dayPlan: DayPlan): DayPlan

    fun save(dayPlans: Set<DayPlan>)

    fun findAll(criteria: DayPlanCriteria, offset: Int? = null, limit: Int? = null, userId: Int? = null): Set<DayPlan>

    fun count(criteria: DayPlanCriteria, userId: Int? = null): Long

    fun findByDate(date: LocalDate, userId: Int): DayPlan?

    fun delete(date: LocalDate, userId: Int? = null): Boolean

}