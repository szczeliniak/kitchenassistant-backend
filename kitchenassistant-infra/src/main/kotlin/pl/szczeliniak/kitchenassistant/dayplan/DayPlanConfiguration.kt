package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.factories.DayPlanFactory
import pl.szczeliniak.kitchenassistant.dayplan.queries.DayPlanConverter
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlanQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlansQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetRecipesByDayPlanIdQuery
import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.recipe.queries.GetRecipeQuery

@Configuration
class DayPlanConfiguration {

    @Bean
    fun deassignRecipesFromDayPlansCommand(dayPlanDao: DayPlanDao): DeassignRecipesFromDayPlansCommand =
        DeassignRecipesFromDayPlansCommand(dayPlanDao)

    @Bean
    fun dayPlanFacade(
        dayPlanDao: DayPlanDao,
        recipeFacade: RecipeFacade,
        deassignRecipesFromDayPlansCommand: DeassignRecipesFromDayPlansCommand,
        getRecipeQuery: GetRecipeQuery
    ): DayPlanFacade {
        val dayPlanConverter = DayPlanConverter(recipeFacade)
        return DayPlanFacadeImpl(
            GetDayPlanQuery(dayPlanDao, dayPlanConverter),
            GetDayPlansQuery(dayPlanDao, dayPlanConverter),
            AddDayPlanCommand(dayPlanDao, DayPlanFactory()),
            UpdateDayPlanCommand(dayPlanDao),
            AddRecipeToDayPlanCommand(dayPlanDao, recipeFacade),
            DeleteRecipeFromDayPlanCommand(dayPlanDao),
            DeleteDayPlanCommand(dayPlanDao),
            ArchiveDayPlanCommand(dayPlanDao),
            deassignRecipesFromDayPlansCommand,
            ArchiveDayPlansCommand(dayPlanDao),
            GetRecipesByDayPlanIdQuery(dayPlanDao, getRecipeQuery, dayPlanConverter)
        )
    }

}