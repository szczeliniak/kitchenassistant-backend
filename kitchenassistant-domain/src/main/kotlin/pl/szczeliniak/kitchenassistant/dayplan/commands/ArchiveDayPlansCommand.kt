package pl.szczeliniak.kitchenassistant.dayplan.commands

import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.dto.DayPlanCriteria
import java.time.LocalDate

class ArchiveDayPlansCommand(private val dayPlanDao: DayPlanDao) {

    fun execute() {
        val dayPlans = dayPlanDao.findAll(
            DayPlanCriteria(
                archived = false,
                automaticArchiving = true,
                to = LocalDate.now().minusDays(1)
            )
        )
        dayPlans.forEach { it.archived = true }
        dayPlanDao.save(dayPlans)
    }

}