package pl.szczeliniak.kitchenassistant.dayplan

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class DayPlanScheduler(private val dayPlanFacade: DayPlanFacade) {

    private val logger: Logger = LoggerFactory.getLogger(DayPlanScheduler::class.java)

    @Scheduled(cron = "\${scheduling.dayplan.archiving}")
    fun terminateDayPlans() {
        logger.info("Day plan termination has been triggered!")
        dayPlanFacade.triggerArchiving()
        logger.info("Day plan termination has been finished!")
    }

}