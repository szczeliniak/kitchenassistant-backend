package pl.szczeliniak.kitchenassistant.dayplan.db

import java.time.LocalDate

interface DayPlanDao {

    fun save(dayPlan: DayPlan): DayPlan

    fun save(dayPlans: List<DayPlan>)

    fun findAll(
        criteria: DayPlanCriteria,
        userId: Int,
        sort: Sort = Sort.ASC,
        offset: Int? = null,
        limit: Int? = null,
    ): List<DayPlan>

    fun count(criteria: DayPlanCriteria, userId: Int): Long

    fun findById(id: Int, userId: Int): DayPlan?

    fun findByDate(date: LocalDate, userId: Int): DayPlan?

    fun delete(dayPlan: DayPlan)

}