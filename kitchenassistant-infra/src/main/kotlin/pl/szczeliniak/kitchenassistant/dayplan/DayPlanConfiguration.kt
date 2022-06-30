package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.factories.DayPlanFactory
import pl.szczeliniak.kitchenassistant.dayplan.queries.DayPlanConverter
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlanQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlansQuery
import pl.szczeliniak.kitchenassistant.receipt.queries.GetReceiptQuery

@Configuration
class DayPlanConfiguration {

    @Bean
    fun dayPlanFactory(): DayPlanFactory = DayPlanFactory()

    @Bean
    fun addDayPlanCommand(dayPlanDao: DayPlanDao, dayPlanFactory: DayPlanFactory) =
        AddDayPlanCommand(dayPlanDao, dayPlanFactory)

    @Bean
    fun updateDayPlanCommand(dayPlanDao: DayPlanDao) = UpdateDayPlanCommand(dayPlanDao)

    @Bean
    fun dayPlanConverter() = DayPlanConverter()

    @Bean
    fun getDayPlanQuery(dayPlanDao: DayPlanDao, dayPlanConverter: DayPlanConverter) =
        GetDayPlanQuery(dayPlanDao, dayPlanConverter)

    @Bean
    fun getDayPlansQuery(dayPlanDao: DayPlanDao, dayPlanConverter: DayPlanConverter) =
        GetDayPlansQuery(dayPlanDao, dayPlanConverter)

    @Bean
    fun addReceiptToDayPlan(dayPlanDao: DayPlanDao, getReceiptQuery: GetReceiptQuery) =
        AddReceiptToDayPlanCommand(dayPlanDao, getReceiptQuery)

    @Bean
    fun deleteReceiptFromDayPlan(dayPlanDao: DayPlanDao) = DeleteReceiptFromDayPlanCommand(dayPlanDao)

    @Bean
    fun deleteDayPlan(dayPlanDao: DayPlanDao) = DeleteDayPlanCommand(dayPlanDao)

    @Bean
    fun archiveDayPlan(dayPlanDao: DayPlanDao) = ArchiveDayPlanCommand(dayPlanDao)

}