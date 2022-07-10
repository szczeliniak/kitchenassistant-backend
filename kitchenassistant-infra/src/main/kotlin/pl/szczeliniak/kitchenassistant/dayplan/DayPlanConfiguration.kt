package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.factories.DayPlanFactory
import pl.szczeliniak.kitchenassistant.dayplan.queries.DayPlanConverter
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlanQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlansQuery
import pl.szczeliniak.kitchenassistant.receipt.ReceiptFacade

@Configuration
class DayPlanConfiguration {

    @Bean
    fun deassignReceiptsFromDayPlansCommand(dayPlanDao: DayPlanDao): DeassignReceiptsFromDayPlansCommand =
        DeassignReceiptsFromDayPlansCommand(dayPlanDao)

    @Bean
    fun dayPlanFacade(
        dayPlanDao: DayPlanDao,
        receiptFacade: ReceiptFacade,
        deassignReceiptsFromDayPlansCommand: DeassignReceiptsFromDayPlansCommand
    ): DayPlanFacade {
        val dayPlanConverter = DayPlanConverter(receiptFacade)
        return DayPlanFacadeImpl(
            GetDayPlanQuery(dayPlanDao, dayPlanConverter),
            GetDayPlansQuery(dayPlanDao, dayPlanConverter),
            AddDayPlanCommand(dayPlanDao, DayPlanFactory()),
            UpdateDayPlanCommand(dayPlanDao),
            AddReceiptToDayPlanCommand(dayPlanDao, receiptFacade),
            DeleteReceiptFromDayPlanCommand(dayPlanDao),
            DeleteDayPlanCommand(dayPlanDao),
            ArchiveDayPlanCommand(dayPlanDao),
            deassignReceiptsFromDayPlansCommand,
            ArchiveDayPlansCommand(dayPlanDao)
        )
    }

}