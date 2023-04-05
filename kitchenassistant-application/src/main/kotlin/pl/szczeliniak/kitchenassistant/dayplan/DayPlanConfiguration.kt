package pl.szczeliniak.kitchenassistant.dayplan

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pl.szczeliniak.kitchenassistant.dayplan.commands.*
import pl.szczeliniak.kitchenassistant.dayplan.commands.factories.DayPlanFactory
import pl.szczeliniak.kitchenassistant.dayplan.db.DayPlanDao
import pl.szczeliniak.kitchenassistant.dayplan.queries.DayPlanConverter
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlanQuery
import pl.szczeliniak.kitchenassistant.dayplan.queries.GetDayPlansQuery
import pl.szczeliniak.kitchenassistant.recipe.RecipeFacade
import pl.szczeliniak.kitchenassistant.recipe.queries.GetRecipeQuery

@Configuration
class DayPlanConfiguration {

    @Bean
    fun deleteRecipesFromDayPlansCommand(dayPlanDao: DayPlanDao): DeleteRecipesFromDayPlansCommand =
            DeleteRecipesFromDayPlansCommand(dayPlanDao)

    @Bean
    fun dayPlanFacade(
            dayPlanDao: DayPlanDao,
            recipeFacade: RecipeFacade,
            getRecipeQuery: GetRecipeQuery
    ): DayPlanFacade {
        val dayPlanConverter = DayPlanConverter(recipeFacade)
        return DayPlanFacade(
                GetDayPlanQuery(dayPlanDao, dayPlanConverter),
                GetDayPlansQuery(dayPlanDao, dayPlanConverter),
                AddRecipeToDayPlanCommand(dayPlanDao, recipeFacade, DayPlanFactory()),
                DeleteRecipeFromDayPlanCommand(dayPlanDao),
                DeleteDayPlanCommand(dayPlanDao),
                ArchiveDayPlanCommand(dayPlanDao),
                UpdateDayPlanCommand(dayPlanDao),
                ArchiveDayPlansCommand(dayPlanDao)
        )
    }

}